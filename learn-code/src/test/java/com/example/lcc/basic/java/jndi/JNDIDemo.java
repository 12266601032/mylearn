package com.example.lcc.basic.java.jndi;

import com.sun.jndi.fscontext.FSContextFactory;
import com.sun.jndi.fscontext.RefFSContextFactory;
import org.junit.Test;

import javax.naming.*;
import javax.naming.spi.ObjectFactory;
import java.rmi.Remote;
import java.util.Date;
import java.util.Hashtable;

/**
 * http://lib.csdn.net/article/java/2340
 *
 * @date 2018/3/26
 */
public class JNDIDemo {
    public static class ReferenceableObj implements Referenceable {
        @Override
        public Reference getReference() throws NamingException {
            return new Reference(getClass().getName(), ReferenceableObj.class.getName(), null);
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
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, RefFSContextFactory.class.getName());
        env.put(Context.PROVIDER_URL, "file:/e:/sample");
        Context context = new InitialContext(env);


        context.rebind("DBService", new ReferenceableObj());
        System.out.println(context.lookup("DBService"));
        context.close();
    }

}
