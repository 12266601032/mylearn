package com.example.java.jndi;

import com.sun.jndi.fscontext.FSContextFactory;
import com.sun.jndi.fscontext.RefFSContextFactory;
import org.junit.Test;

import javax.naming.*;
import javax.naming.spi.ObjectFactory;
import java.rmi.Remote;
import java.util.Date;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * http://lib.csdn.net/article/java/2340
 *
 * @date 2018/3/26
 */
public class JNDIDemo {
    public static class ReferenceableObj implements Referenceable {
        @Override
        public Reference getReference() throws NamingException {
            return new Reference(getClass().getName(), ReferenceableObjFactory.class.getName(), null);
        }

    }
    public static class ReferenceableObjFactory implements ObjectFactory  {

        @Override
        public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) throws Exception {
            if(obj instanceof Reference){
                Reference ref=(Reference)obj;
                ReferenceableObj db= new ReferenceableObj();
                return db;
            }
            return null;
        }
    }

    @Test
    public void jndi() throws NamingException {
        /**
         * 通过jndi bind的对象 name对应的obj必须是
         * javax.naming.Referenceable或者javax.naming.Reference
         * 主要是获取对应的Reference对象 来保存绑定的类与对象工厂ObjectFactory
         * 如下就是会在file:/e:/sample目录下生成.bindings文件，其中配置了
         * DBService/FactoryName=xxxx、DBService/ClassName=xxx
         * 然后在lookup时通过.bindings文件中的配置构造出Reference
         * 通过NamingManager.getObjectInstance()
         * 来实例化对象。
         */
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, RefFSContextFactory.class.getName());
        //env.put(Context.PROVIDER_URL, "file:/e:/sample");
        Context context = new InitialContext(env);


        //context.rebind("DBService", new ReferenceableObj());
        System.out.println(context.lookup("file:/e:/sample/DBService"));
        context.close();
    }

}
