package com.qyy.mvcframework;

import com.qyy.mvcframework.annotation.QYYAutoWried;
import com.qyy.mvcframework.annotation.QYYController;
import com.qyy.mvcframework.annotation.QYYRequestMapping;
import com.qyy.mvcframework.annotation.QYYService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * @author Qyy
 * @date 2023/8/9 11:11
 */
public class YYDispatchServlet extends HttpServlet {
    private Properties contextConfig=new Properties();
    private List<String> classNames=new ArrayList<String>();
    private Map<String,Object> ioc=new HashMap<String,Object>();//保存所有扫描到的类的实例
    private Map<String,Method> handlerMapping=new HashMap<String, Method>();//存Controller中的url和Method的关系
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        6、根据URL委派给具体的调用方法
        try {
            try {
                doDispatch(req,resp);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
            resp.getWriter().write("500 ");
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replaceAll(contextPath, "").replaceAll("/+", "/");
        if (!this.handlerMapping.containsKey(url)){
            resp.getWriter().write("404 ");
            return;
        }
        Method method=(Method)this.handlerMapping.get(url);
        Map<String,String[]> params = req.getParameterMap();
        String beanName=toLowerFirstCase(method.getDeclaringClass().getSimpleName());
        method.invoke(ioc.get(beanName),new Object[]{req,resp,params.get("name")[0]});
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
//        1、加载配置文件
            loaderConfigFile(config.getInitParameter("contextConfigLocation"));
//        2、扫描相关的类
            scanner(contextConfig.getProperty("scanPackage"));
//        3、初始化IoC容器，将扫描到的类进行实例化，缓存到IoC容器中
            doInstance();
//        4、完成依赖注入
            doAutoWired();
//        5、初始化HandlerMapping
            doInitHandlerMapping();
            System.out.println("Qyy SpringFramework is init.");
    }

    private void doInitHandlerMapping() {
        if (ioc.isEmpty()) {
            return;
        }
        for (Map.Entry<String,Object> entry:ioc.entrySet()){
            Class<?> clazz=entry.getValue().getClass();
            if (!clazz.isAnnotationPresent(QYYController.class)){
                continue;
            }
            String baseUrl=null;
            if (clazz.isAnnotationPresent(QYYRequestMapping.class)){
                QYYRequestMapping requestMapping = clazz.getAnnotation(QYYRequestMapping.class);
                baseUrl=requestMapping.value();
            }
            for (Method method:clazz.getMethods()){
                if (!method.isAnnotationPresent(QYYRequestMapping.class)){
                    continue;
                }
                QYYRequestMapping requestMapping = method.getAnnotation(QYYRequestMapping.class);
                String url =("/"+baseUrl+"/"+requestMapping.value()).replaceAll("/+","/");
                handlerMapping.put(url,method);
                System.out.println("Mapping:"+url+"---->"+method);
            }
        }
    }

    private void doAutoWired() {
        if (ioc.isEmpty()){
            return;
        }
        //遍历Map把对象
        for (Map.Entry<String,Object> entry:ioc.entrySet()){
            for (Field field:entry.getValue().getClass().getDeclaredFields()){
                //判断是否加了注解
                if (!field.isAnnotationPresent(QYYAutoWried.class)){ continue;}
                QYYAutoWried autoWried = field.getAnnotation(QYYAutoWried.class);
                String beanName = autoWried.value().trim();
                if ("".equals(beanName)){
                    beanName=field.getType().getName();
                }
                //强制访问，强吻
                field.setAccessible(true);
                try {

                    field.set(entry.getValue(),ioc.get(beanName));

                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void doInstance()  {
        if (classNames.isEmpty()){
            return;
        }
        for (String className:classNames){
            try {
                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(QYYController.class)){
                    String beanName=toLowerFirstCase(clazz.getSimpleName());
                    Object instance = clazz.newInstance();
                    ioc.put(beanName,instance);
                }else if (clazz.isAnnotationPresent(QYYService.class)){

                    String beanName=toLowerFirstCase(clazz.getSimpleName());
                    QYYService service = clazz.getAnnotation(QYYService.class);
                    if (!service.value().equals("")){
                        beanName=service.value();
                    }
                    Object instance = clazz.newInstance();
                    ioc.put(beanName,instance);
                    //如果是接口，只能初始化实现类
                    for (Class<?> i:clazz.getInterfaces()){
                        if (ioc.containsKey(i.getName())){
                            throw new Exception("多个实现类，实例化失败");
                        }
                        ioc.put(i.getName(),instance);
                    }
                }else{
                    continue;
                }



            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    private String toLowerFirstCase(String className) {
        char[] chars=className.toCharArray();
        chars[0]+=32;//大写和小写字母的ASCII码相差32
        return String.valueOf(chars);
    }

    private void scanner(String scanPackage) {
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));
        File classPath = new File(url.getFile());
        for (File file:classPath.listFiles()){
            if (file.isDirectory()){
                scanner(scanPackage+"."+file.getName());
            }else {
                if (!file.getName().endsWith(".class")){
                    continue;
                }
                String className=(scanPackage+"."+file.getName().replace(".class",""));
                classNames.add(className);
            }

        }
        //scanPackage = com.gupaoedu.demo ，存储的是包路径
        //转换为文件路径，实际上就是把.替换为/就OK了
        //classpath
        /*URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.","/"));
        File classPath = new File(url.getFile());
        for (File file : classPath.listFiles()) {
            if(file.isDirectory()){
                scanner(scanPackage + "." + file.getName());
            }else{
                if(!file.getName().endsWith(".class")){ continue;}
                String className = (scanPackage + "." + file.getName().replace(".class",""));
                classNames.add(className);
            }
        }*/
    }
//根据contextConfigLocation
    private void loaderConfigFile(String contextConfigLocation) {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
        try {
            contextConfig.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if (is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }
}
