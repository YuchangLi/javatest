package com.jvm.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.expression.AnnotationValue;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import static java.lang.System.out;

/**
 * @author liyuchang
 * @date 2023/07/27
 */
public class QdocMain {
    public static void main(String[] args) throws Exception {
        JavaProjectBuilder javaProjectBuilder = new JavaProjectBuilder();
        String filePath = "/Users/banma-3449/git/github/YuchangLi/javatest/jvm-demo/src/main/java/com/jvm/demo/DdocDemo.java";
        File file = new File(filePath);
        out.println("file.exists() = " + file.exists());
        javaProjectBuilder.addSource(file);
        Collection<JavaClass> classes = javaProjectBuilder.getClasses();
        classes.forEach(javaClass -> {
            // System.out.println("javaClass = " + JSON.toJSONString(javaClass));
            out.println("javaClass = " + javaClass);
            out.println("javaClass.getCanonicalName() = " + javaClass.getCanonicalName());
            try {
                Class<?> clz = Class.forName(javaClass.getCanonicalName());
                out.println("clz == DdocDemo.class = " + (clz == DdocDemo.class));
                JavaAnnotation classAnno = javaClass.getAnnotations().get(0);
                out.println("classAnno.getNamedParameterMap() = " + classAnno.getNamedParameterMap());

                Map<String, AnnotationValue> propertyMap = classAnno.getPropertyMap();
                out.println("classAnno.getPropertyMap() = " + propertyMap);
                JavaMethod javaMethod = javaClass.getMethods().get(0);
                JavaAnnotation methodAnno = javaMethod.getAnnotations().get(0);
                Class<?> aClass = Class.forName(methodAnno.getType().getFullyQualifiedName());
                out.println("(aClass == AnnoTest.class) = " + (aClass == AnnoTest.class));
                System.out.println("methodAnno.getPropertyMap() = " + methodAnno.getPropertyMap());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
