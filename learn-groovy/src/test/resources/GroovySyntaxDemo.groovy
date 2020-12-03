/**
 * 文档注释
 */
class GroovySyntaxDemo {
    /*
    多行注释
     */
    private String name; //单行注释
    public def obj; //声明任意类型变量，类似于Object
    /**
     * 构造器
     */
    GroovySyntaxDemo() {
        obj = 1;//变量赋值
        name = "abc"
    }
    void stringDemo() {
        //等价于java中的singleQuotedString = "字符串"
        def singleQuotedString = '字符串'
        //字符串连接
        assert 'ab' == 'a' + 'b'
        //多行连续字符串
        def tripleSingleQuotedString = '''
'''
    }
    /**
     * 定义方法
     * @param text 参数
     * @return 返回值
     */
    String greet(String text) {
        "hello ${text}" //返回值，${text}自动替换为text值
    }

}

/*
关键字：
as 类型转换Integer x = 123 String s = x as String
assert 断言
def 用在方法声明上表示任意返回值、任意入参（类似于js中的函数），用于声明变量类似于使用Object类型，groovy自动类型推断
trait 特有实现，类似于js混入
in 用于for(x in [1, 3, 4]){..} x为数组元素、集合元素、map的entry

const
break
case
catch
class
continue
default
do
else
enum
extends
false
finally
for
goto
if
implements
import
instanceof
interface
new
null
package
return
super
switch
this
throw
throws
true
try
while
 */
