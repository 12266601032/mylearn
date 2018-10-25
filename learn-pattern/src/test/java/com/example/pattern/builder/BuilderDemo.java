package com.example.pattern.builder;

import org.junit.Test;

/**
 * Builder模式是对于 利用重载构造器 来构造对象的一个替代解决方法
 * 随着参数增加时，使用构造器的方式解决 对应的构造器可能需要指数级增加，而使用builder模式
 * 是给对应的类创建一个builder对象每次接口一步参数，最终再一次性构造出目标对象。
 * 虽然使用javaBean 也可以有类似的效果（创建好对象 然后挨个set），但是java bean 会使对象构建过程的状态在一直变化。
 * <p>
 * 使用builder来构造对象是最好的方式！
 */
public class BuilderDemo {


    @Test
    public void testBuilderHero() {
        /*
         * 可以看到 使用builder来构造对象 会显得很流畅，而且在对象构造过程中，不存在中间不一致的状态。
         */
        Hero mage =
                new Hero.Builder(Profession.MAGE, "Riobard").withHairColor(HairColor.BLACK)
                        .withWeapon(Weapon.DAGGER).build();

        System.out.println(mage);

        Hero warrior =
                new Hero.Builder(Profession.WARRIOR, "Amberjill").withHairColor(HairColor.BLOND)
                        .withHairType(HairType.LONG_CURLY).withArmor(Armor.CHAIN_MAIL).withWeapon(Weapon.SWORD)
                        .build();

        System.out.println(warrior);

        Hero thief =
                new Hero.Builder(Profession.THIEF, "Desmond").withHairType(HairType.BALD)
                        .withWeapon(Weapon.BOW).build();
        System.out.println(thief);
    }


    public static final class Hero {
        private final Profession profession;
        private final String name;
        private final HairType hairType;
        private final HairColor hairColor;
        private final Armor armor;
        private final Weapon weapon;

        private Hero(Builder builder) {
            this.profession = builder.profession;
            this.name = builder.name;
            this.hairColor = builder.hairColor;
            this.hairType = builder.hairType;
            this.weapon = builder.weapon;
            this.armor = builder.armor;
        }

        public Profession getProfession() {
            return profession;
        }

        public String getName() {
            return name;
        }

        public HairType getHairType() {
            return hairType;
        }

        public HairColor getHairColor() {
            return hairColor;
        }

        public Armor getArmor() {
            return armor;
        }

        public Weapon getWeapon() {
            return weapon;
        }

        @Override
        public String toString() {

            StringBuilder sb = new StringBuilder();
            sb.append("This is a ")
                    .append(profession)
                    .append(" named ")
                    .append(name);
            if (hairColor != null || hairType != null) {
                sb.append(" with ");
                if (hairColor != null) {
                    sb.append(hairColor).append(' ');
                }
                if (hairType != null) {
                    sb.append(hairType).append(' ');
                }
                sb.append(hairType != HairType.BALD ? "hair" : "head");
            }
            if (armor != null) {
                sb.append(" wearing ").append(armor);
            }
            if (weapon != null) {
                sb.append(" and wielding a ").append(weapon);
            }
            sb.append('.');
            return sb.toString();
        }

        /**
         * The builder clazz.
         */
        public static class Builder {

            private final Profession profession;
            private final String name;
            private HairType hairType;
            private HairColor hairColor;
            private Armor armor;
            private Weapon weapon;

            /**
             * Constructor
             */
            public Builder(Profession profession, String name) {
                if (profession == null || name == null) {
                    throw new IllegalArgumentException("profession and name can not be null");
                }
                this.profession = profession;
                this.name = name;
            }

            public Builder withHairType(HairType hairType) {
                this.hairType = hairType;
                return this;
            }

            public Builder withHairColor(HairColor hairColor) {
                this.hairColor = hairColor;
                return this;
            }

            public Builder withArmor(Armor armor) {
                this.armor = armor;
                return this;
            }

            public Builder withWeapon(Weapon weapon) {
                this.weapon = weapon;
                return this;
            }

            public Hero build() {
                return new Hero(this);
            }
        }
    }


    public enum Armor {

        CLOTHES("clothes"), LEATHER("leather"), CHAIN_MAIL("chain mail"), PLATE_MAIL("plate mail");

        private String title;

        Armor(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    public enum HairColor {

        WHITE, BLOND, RED, BROWN, BLACK;

        @Override
        public String toString() {
            return name().toLowerCase();
        }

    }

    public enum HairType {

        BALD("bald"), SHORT("short"), CURLY("curly"), LONG_STRAIGHT("long straight"), LONG_CURLY(
                "long curly");

        private String title;

        HairType(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    public enum Profession {

        WARRIOR, THIEF, MAGE, PRIEST;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public enum Weapon {

        DAGGER, SWORD, AXE, WARHAMMER, BOW;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

}
