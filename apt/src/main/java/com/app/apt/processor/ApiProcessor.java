package com.app.apt.processor;

import com.app.annotation.apt.ApiFactory;
import com.app.apt.AnnotationProcessor;
import com.app.apt.inter.IProcessor;
import com.app.apt.util.Utils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

import javax.annotation.processing.FilerException;
import javax.annotation.processing.RoundEnvironment;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;

import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

/**
 * Created by baixiaokang on 16/12/28.
 */

public class ApiProcessor implements IProcessor {
    @Override
    public void process(RoundEnvironment roundEnv, AnnotationProcessor mAbstractProcessor) {

        String DATA_ARR_CLASS = "DataArr";
//        TypeSpec.Builder tb = classBuilder(CLASS_NAME).addModifiers(PUBLIC, FINAL).addJavadoc("@ API工厂 此类由apt自动生成");
        try {
            for (TypeElement element : ElementFilter.typesIn(roundEnv.getElementsAnnotatedWith(ApiFactory.class))) {
                mAbstractProcessor.mMessager.printMessage(Diagnostic.Kind.NOTE, "正在处理: " + element.toString());
                String ELEMENT_NAME = element.getSimpleName().toString();
                String CLASS_NAME = ELEMENT_NAME + "Api";

                //创建一个类
                TypeSpec.Builder tb = TypeSpec.classBuilder(CLASS_NAME).addModifiers(PUBLIC).addJavadoc("@ API工厂 此类由apt自动生成");
                //添加一个属性（单列模式）
                FieldSpec.Builder instanceField =
                        FieldSpec.builder(ClassName.get(Utils.PackageName, CLASS_NAME), "instance")
                                .addJavadoc("@此属性由apt自动生成")
                                .addModifiers(STATIC);
                tb.addField(instanceField.build());
                //添加一个属性Retrofit
                FieldSpec.Builder retrofitField =
                        FieldSpec.builder(ClassName.get("retrofit2", "Retrofit"), "retrofit")
                                .addJavadoc("@此属性由apt自动生成")
                                .addModifiers(PUBLIC);
                tb.addField(retrofitField.build());
                //添加一个属性service
                FieldSpec.Builder serviceField =
                        FieldSpec.builder(ClassName.get(element.getQualifiedName().toString().replace("." + ELEMENT_NAME,""),ELEMENT_NAME), "service")
                                .addJavadoc("@此属性由apt自动生成")
                                .addModifiers(PUBLIC);
                tb.addField(serviceField.build());
                //添加一个获取单列的方法
                MethodSpec.Builder getInstance =
                        MethodSpec.methodBuilder("getInstance")
                                .addJavadoc("@此方法由apt自动生成")
                                .addModifiers(PUBLIC, STATIC);
                getInstance.beginControlFlow("if (instance == null)")
                        .beginControlFlow("synchronized (" + CLASS_NAME + ".class )")
                        .beginControlFlow("if (instance == null)")
                        .addStatement("instance = new " + CLASS_NAME + "()")
                        .endControlFlow()
                        .endControlFlow()
                        .endControlFlow()
                        .addStatement(" return instance")
                        .returns(ClassName.get(Utils.PackageName, CLASS_NAME));
                tb.addMethod(getInstance.build());
                //构造方法私有
                MethodSpec.Builder construct =
                        MethodSpec.constructorBuilder()
                                .addJavadoc("@此方法由apt自动生成")
                                .addModifiers(PRIVATE);
                ClassName retrofitProvider = ClassName.get("com.ice.library.http.Provider","RetrofitProvider");
                construct.addStatement("retrofit = $T.retrofit()",retrofitProvider)
                        .addStatement("service = retrofit.create(" + ELEMENT_NAME + ".class)");
                tb.addMethod(construct.build());
                JavaFile javaFile = JavaFile.builder(Utils.PackageName, tb.build()).build();// 生成源代码
                javaFile.writeTo(mAbstractProcessor.mFiler);// 在 app module/build/generated/source/apt 生成一份源代码
            }

        } catch (FilerException e) {
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
