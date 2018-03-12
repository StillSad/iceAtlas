package com.app.apt.processor;

import com.app.annotation.apt.ApiFactory;
import com.app.annotation.apt.Encrypted;
import com.app.annotation.apt.Params;
import com.app.apt.AnnotationProcessor;
import com.app.apt.inter.IProcessor;
import com.app.apt.util.Utils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.FilerException;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;


import static com.squareup.javapoet.TypeSpec.classBuilder;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

/**
 * Created by baixiaokang on 16/12/28.
 */

public class ApiFactoryProcessor implements IProcessor {
    @Override
    public void process(RoundEnvironment roundEnv, AnnotationProcessor mAbstractProcessor) {

        String DATA_ARR_CLASS = "DataArr";
//        TypeSpec.Builder tb = classBuilder(CLASS_NAME).addModifiers(PUBLIC, FINAL).addJavadoc("@ API工厂 此类由apt自动生成");
        try {
            for (TypeElement element : ElementFilter.typesIn(roundEnv.getElementsAnnotatedWith(ApiFactory.class))) {
                mAbstractProcessor.mMessager.printMessage(Diagnostic.Kind.NOTE, "正在处理: " + element.toString());
                String ELEMENT_NAME = element.getSimpleName().toString();
                String CLASS_NAME = ELEMENT_NAME + "Factory";
                //类
                TypeSpec.Builder tb = classBuilder(CLASS_NAME).addModifiers(PUBLIC, FINAL).addJavadoc("@ API工厂 此类由apt自动生成");
                //方法
                for (Element e : element.getEnclosedElements()) {
                    ExecutableElement executableElement = (ExecutableElement) e;

//                    String classNameString = "java.lang.String";
                    MethodSpec.Builder methodBuilder =
                            MethodSpec.methodBuilder(e.getSimpleName().toString())
                                    .addJavadoc("@此方法由apt自动生成")
//                                    .addJavadoc("@此方法由apt自动生成" +  annotation.value().length)
                                    .addModifiers(PUBLIC, STATIC);
                    //参数注解
                    Params paramsAnnotation = executableElement.getAnnotation(Params.class);


                    if (paramsAnnotation == null) {
                        normalParams(ELEMENT_NAME, tb, e, executableElement, methodBuilder);
                    } else {
                        mapParams(ELEMENT_NAME, tb, e, executableElement, methodBuilder, paramsAnnotation);
                    }
                    tb.addMethod(methodBuilder.build());
                }
                //
                JavaFile javaFile = JavaFile.builder(Utils.PackageName, tb.build()).build();// 生成源代码
                javaFile.writeTo(mAbstractProcessor.mFiler);// 在 app module/build/generated/source/apt 生成一份源代码
                //
            }
//            JavaFile javaFile = JavaFile.builder(Utils.PackageName, tb.build()).build();// 生成源代码
//            javaFile.writeTo(mAbstractProcessor.mFiler);// 在 app module/build/generated/source/apt 生成一份源代码
        } catch (FilerException e) {
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mapParams(String ELEMENT_NAME, TypeSpec.Builder tb, Element e, ExecutableElement executableElement, MethodSpec.Builder methodBuilder, Params paramsAnnotation) {

        methodBuilder.returns(TypeName.get(executableElement.getReturnType()));


        String paramsString = "";


        //
        for (VariableElement ep : executableElement.getParameters()) {
            if (!TypeName.get(ep.asType()).toString().contains("HashMap")) {
                methodBuilder.addParameter(TypeName.get(ep.asType()), ep.getSimpleName().toString());
                paramsString += ep.getSimpleName().toString() + ",";
            }
        }

        for (String param : paramsAnnotation.value()) {
            methodBuilder.addParameter(String.class, param);
        }


        //创建一个map保存参数
        methodBuilder.addStatement(
                "$T<$T,$T> params = new HashMap()",
                ClassName.get("java.util", "HashMap"),
                ClassName.get("java.lang", "String"),
                ClassName.get("java.lang", "String"));

        for (String param : paramsAnnotation.value()) {
//                            params.put("appName",appName);
            methodBuilder.addStatement("params.put(\"" + param + "\"," + param + ")");

        }

        paramsString += "params";

        methodBuilder.addStatement(
                "return $T.getInstance()" +
                        ".service.$L($L)"
                , ClassName.get(Utils.PackageName, ELEMENT_NAME + "Api")
                , e.getSimpleName().toString()
                , paramsString);


    }

    private void normalParams(String ELEMENT_NAME, TypeSpec.Builder tb, Element e, ExecutableElement executableElement, MethodSpec.Builder methodBuilder) {

        methodBuilder.returns(TypeName.get(executableElement.getReturnType()));
        String paramsString = "";
        for (VariableElement ep : executableElement.getParameters()) {
            methodBuilder.addParameter(TypeName.get(ep.asType()), ep.getSimpleName().toString());
            paramsString += ep.getSimpleName().toString() + ",";
        }
        if (paramsString.endsWith(",")) {
            paramsString = paramsString.substring(0, paramsString.length() - 1);
        }
        methodBuilder.addStatement(
                "return $T.getInstance()" +
                        ".service.$L($L)"
                , ClassName.get(Utils.PackageName, ELEMENT_NAME + "Api")
                , e.getSimpleName().toString()
                , paramsString);
    }
}
