package com.app.apt.processor;

import com.app.annotation.apt.ApiFactory;
import com.app.annotation.apt.MvpActivity;
import com.app.annotation.apt.MvpFragment;
import com.app.apt.AnnotationProcessor;
import com.app.apt.inter.IProcessor;
import com.app.apt.util.Utils;
import com.google.common.base.Joiner;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import javax.annotation.processing.FilerException;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;

import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.PROTECTED;
import static javax.lang.model.element.Modifier.ABSTRACT;

/**
 * Created by ICE on 2017/8/10.
 */

public class DiProcessor implements IProcessor {

    private static final String PATH_BASE_LIB = "com.ice.library";
    private static final String PATH_DAGGER = "dagger";
    private static final String PATH_ANDROID_APP = "android.app";
    private static final String PATH_ANDROID_CONTENT = "android.content";
    private static final String PATH_ANDROID_DATABINDING = "android.databinding";
    private static final String PATH_DI_MODULE = ".di.module";
    private static final String PATH_DI_COMPONENT = ".di.component";
    private static final String PATH_DI_SCOPE = ".di.scope";
    private static final String PATH_BASE = ".base";
    private static final String PATH_MVP = ".mvp";
    private static final String PATH_ACTIVITY = ".activity";
    private static final String PATH_FRAGMENT = ".fragment";



    private String modelName = "";
    private AnnotationProcessor mAbstractProcessor;

    @Override
    public void process(RoundEnvironment roundEnv, AnnotationProcessor mAbstractProcessor) {
        this.mAbstractProcessor = mAbstractProcessor;
        activityComponentProcess(roundEnv);
        fragmentComponentProcess(roundEnv);
    }

    private void activityComponentProcess(RoundEnvironment roundEnv) {
//        String ELEMENT_NAME = element.getSimpleName().toString();


        ArrayList<MethodSpec.Builder> methodBuilders = new ArrayList<>();
        System.out.println("------------------");
        System.out.println("ActivityComponent");
        try {
            System.out.println("TypeElements size :" + ElementFilter.typesIn(roundEnv.getElementsAnnotatedWith(MvpActivity.class)).size());
            System.out.println("modelName :" + modelName);
            for (TypeElement element : ElementFilter.typesIn(roundEnv.getElementsAnnotatedWith(MvpActivity.class))) {
                //获取当前模块包名
                if (modelName.isEmpty()) {
                    modelName = getModelName(element);
                }

                mAbstractProcessor.mMessager.printMessage(Diagnostic.Kind.NOTE, "正在处理: " + element.toString());

                MethodSpec.Builder injectFun
                        = MethodSpec.methodBuilder("inject")
                        .addParameter(ClassName.get(element), "activity")
                        .addJavadoc("@此方法由apt自动生成")
                        .addModifiers(PUBLIC, ABSTRACT);
                methodBuilders.add(injectFun);

            }




            System.out.println("methodBuilders size :" + methodBuilders.size());
            System.out.println("------------------");

            if (methodBuilders.isEmpty()) return;

            //生成ActivityModule
            activityModuleProcess();
            compinentActivityProcess();

            //创建一个接口
            TypeSpec.Builder tb = TypeSpec.interfaceBuilder("ActivityComponent").addModifiers(PUBLIC).addJavadoc("@ API工厂 此类由apt自动生成");
            //添加注解ActivityScope
            tb.addAnnotation(ClassName.get(PATH_BASE_LIB + PATH_DI_SCOPE, "ActivityScope"));
            //添加注解Component
            AnnotationSpec.Builder componentSpec = AnnotationSpec.builder(ClassName.get(PATH_DAGGER, "Component"));
            //Component参数AppComponent.class
            componentSpec.addMember("dependencies", "$T.$L", ClassName.get(PATH_BASE_LIB + PATH_DI_COMPONENT, "AppComponent"), "class");
            //Component参数ActivityModule.class
            componentSpec.addMember("modules", "$T.$L", ClassName.get(modelName + PATH_DI_MODULE, "ActivityModule"), "class");
            tb.addAnnotation(componentSpec.build());

            for (MethodSpec.Builder methodBuilder : methodBuilders) {
                tb.addMethod(methodBuilder.build());
            }

            JavaFile javaFile = JavaFile.builder(modelName + PATH_DI_COMPONENT, tb.build()).build();// 生成源代码
            javaFile.writeTo(mAbstractProcessor.mFiler);// 在 app module/build/generated/source/apt 生成一份源代码
        } catch (FilerException e) {
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void activityModuleProcess() {

        try {

            if (modelName.isEmpty()) return;
            System.out.println("------------------");
            System.out.println("生成ActivityModule");
            System.out.println("modelName :" + modelName);
            System.out.println("------------------");
            //创建一个接口
            TypeSpec.Builder tb = TypeSpec.classBuilder("ActivityModule").addModifiers(PUBLIC).addJavadoc("@ API工厂 此类由apt自动生成");
            //添加注解Module
            tb.addAnnotation(ClassName.get(PATH_DAGGER, "Module"));
            //            private Activity mActivity;
            FieldSpec.Builder mFragmentField =
                    FieldSpec.builder(ClassName.get(PATH_ANDROID_APP, "Activity"), "mActivity")
                            .addJavadoc("@此属性由apt自动生成")
                            .addModifiers(PRIVATE);
            tb.addField(mFragmentField.build());

//
//            public ActivityModule(Activity activity) {
//                mActivity = activity;
//            }


            MethodSpec.Builder activityModuleBuilder
                    = MethodSpec.constructorBuilder()
                    .addJavadoc("@此方法由apt自动生成")
                    .addModifiers(PUBLIC)
                    .addParameter(ClassName.get(PATH_ANDROID_APP, "Activity"), "activity")
                    .addStatement("this.mActivity = activity");
            tb.addMethod(activityModuleBuilder.build());


//            @Provides
//            @ActivityScope
//            public Context provideContext() {
//                return mActivity;
//            }

            MethodSpec.Builder provideContextBuilder
                    = MethodSpec.methodBuilder("provideContext")
                    .addJavadoc("@此方法由apt自动生成")
                    .addModifiers(PUBLIC)
                    .addAnnotation(ClassName.get(PATH_DAGGER, "Provides"))
                    .addAnnotation(ClassName.get(PATH_BASE_LIB + PATH_DI_SCOPE, "ActivityScope"))
                    .addStatement("return mActivity")
                    .returns(ClassName.get(PATH_ANDROID_CONTENT, "Context"));
            tb.addMethod(provideContextBuilder.build());

//            @Provides
//            @ActivityScope
//            public Activity provideActivity() {
//                return mActivity;
//            }
            MethodSpec.Builder provideActivityBuilder
                    = MethodSpec.methodBuilder("provideActivity")
                    .addJavadoc("@此方法由apt自动生成")
                    .addModifiers(PUBLIC)
                    .addAnnotation(ClassName.get(PATH_DAGGER, "Provides"))
                    .addAnnotation(ClassName.get(PATH_BASE_LIB + PATH_DI_SCOPE, "ActivityScope"))
                    .addStatement("return mActivity")
                    .returns(ClassName.get(PATH_ANDROID_APP, "Activity"));
            tb.addMethod(provideActivityBuilder.build());

            JavaFile javaFile = JavaFile.builder(modelName + PATH_DI_MODULE, tb.build()).build();// 生成源代码
            javaFile.writeTo(mAbstractProcessor.mFiler);// 在 app module/build/generated/source/apt 生成一份源代码
        } catch (FilerException e) {
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void compinentActivityProcess() {
        try {

            if (modelName.isEmpty()) return;
            System.out.println("------------------");
            System.out.println("生成CompinentActivity");
            System.out.println("modelName :" + modelName);
            System.out.println("------------------");
            TypeSpec.Builder a = TypeSpec.classBuilder("MvpAvtivity");
            //创建一个接口
            TypeSpec.Builder tb
                    = TypeSpec.classBuilder("ComponentActivity")
                    .addTypeVariable(TypeVariableName.get("P", ClassName.get(PATH_BASE_LIB + PATH_MVP, "BasePresenter")))
                    .addModifiers(PUBLIC, ABSTRACT)
                    .addJavadoc("@ API工厂 此类由apt自动生成");
            tb.superclass(ParameterizedTypeName.get(ClassName.get(PATH_BASE_LIB + PATH_BASE + PATH_ACTIVITY, "MvpAvtivity"), TypeVariableName.get("P")));


//            protected BaseApplication mBaseApplication;
            tb.addField(getFieldBuilder(PATH_BASE_LIB + PATH_BASE,"BaseApplication","mBaseApplication",PROTECTED));
//            protected ActivityComponent mActivityComponent;
            tb.addField(getFieldBuilder(modelName + PATH_DI_COMPONENT,"ActivityComponent","mActivityComponent",PROTECTED));

//            @Override
//            protected void onCreate(@Nullable Bundle savedInstanceState) {
//                super.onCreate(savedInstanceState);
//                initData(savedInstanceState);
//            }

            ParameterSpec.Builder pBuilder
                    = ParameterSpec.builder(ClassName.get("android.os","Bundle"),"savedInstanceState")
                    .addAnnotation(ClassName.get("android.support.annotation","Nullable"));
            MethodSpec.Builder onCreateBuilder
                    = MethodSpec.methodBuilder("onCreate")
                    .addJavadoc("@此方法由apt自动生成")
                    .addModifiers(PROTECTED)
                    .addAnnotation(ClassName.get("java.lang", "Override"))
                    .addParameter(pBuilder.build())
                    .addStatement("super.onCreate(savedInstanceState)")
                    .addStatement("initData(savedInstanceState)");
            tb.addMethod(onCreateBuilder.build());

//            @Override
//            public void setupActivityComponent(AppComponent appComponent) {
//                mBaseApplication = (BaseApplication)getApplication();
//
//                mActivityComponent = DaggerActivityComponent
//                        .builder()
//                        .appComponent(appComponent)
//                        .activityModule(new ActivityModule(this))
//                        .build();
//                initInject(mActivityComponent);
//            }
            MethodSpec.Builder setupActivityComponentBuilder
                    = MethodSpec.methodBuilder("setupActivityComponent")
                    .addJavadoc("@此方法由apt自动生成")
                    .addModifiers(PUBLIC)
                    .addAnnotation(ClassName.get("java.lang", "Override"))
                    .addParameter(ClassName.get(PATH_BASE_LIB + PATH_DI_COMPONENT,"AppComponent"),"appComponent")
                    .addStatement("mBaseApplication = (BaseApplication)getApplication()")
                    .addStatement("mActivityComponent = $T\n" +
                                                        ".builder()\n" +
                                                        ".appComponent(appComponent)\n" +
                                                        ".activityModule(new $T(this))\n" +
                                                        ".build()"
                            ,ClassName.get(modelName + PATH_DI_COMPONENT,"DaggerActivityComponent")
                            ,ClassName.get(modelName + PATH_DI_MODULE,"ActivityModule"))
                    .addStatement("initInject(mActivityComponent)");
            tb.addMethod(setupActivityComponentBuilder.build());

//            //提供AppComponent(提供所有的单例对象)给子类，进行Component依赖
//            protected abstract void initInject(ActivityComponent activityComponent);
            MethodSpec.Builder initInjectBuilder
                    = MethodSpec.methodBuilder("initInject")
                    .addJavadoc("@此方法由apt自动生成\n")
                    .addJavadoc("提供AppComponent(提供所有的单例对象)给子类，进行Component依赖")
                    .addModifiers(PROTECTED,ABSTRACT)
                    .addParameter(ClassName.get(modelName + PATH_DI_COMPONENT,"ActivityComponent"),"activityComponent");
            tb.addMethod(initInjectBuilder.build());

            JavaFile javaFile = JavaFile.builder(modelName + ".base", tb.build()).build();// 生成源代码
            javaFile.writeTo(mAbstractProcessor.mFiler);// 在 app module/build/generated/source/apt 生成一份源代码
        } catch (FilerException e) {
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fragmentComponentProcess(RoundEnvironment roundEnv) {

        ArrayList<MethodSpec.Builder> methodBuilders = new ArrayList<>();

        try {
            for (TypeElement element : ElementFilter.typesIn(roundEnv.getElementsAnnotatedWith(MvpFragment.class))) {
                if (modelName.isEmpty()) {
                    modelName = getModelName(element);
                }
                mAbstractProcessor.mMessager.printMessage(Diagnostic.Kind.NOTE, "正在处理: " + element.toString());


                MethodSpec.Builder injectFun =
                        MethodSpec.methodBuilder("inject")
                                .addParameter(ClassName.get(element), "fragment")
                                .addJavadoc("@此方法由apt自动生成")
                                .addModifiers(PUBLIC, ABSTRACT);
                methodBuilders.add(injectFun);

            }

            if (methodBuilders.isEmpty()) return;
            //生成fragmentModule
            fragmentModuleProcess();
            compinentFragmentProcess();

            //创建一个接口
            TypeSpec.Builder tb = TypeSpec.interfaceBuilder("FragmentComponent").addModifiers(PUBLIC).addJavadoc("@ API工厂 此类由apt自动生成");
            //添加注解ActivityScope
            tb.addAnnotation(ClassName.get(PATH_BASE_LIB + PATH_DI_SCOPE, "FragmentScope"));
            //添加注解Component
            AnnotationSpec.Builder componentSpec = AnnotationSpec.builder(ClassName.get(PATH_DAGGER, "Component"));
            //Component参数AppComponent.class
            componentSpec.addMember("dependencies", "$T.$L", ClassName.get(PATH_BASE_LIB + PATH_DI_COMPONENT, "AppComponent"), "class");
            //Component参数ActivityModule.class
            componentSpec.addMember("modules", "$T.$L", ClassName.get(modelName + PATH_DI_MODULE, "FragmentModule"), "class");
            tb.addAnnotation(componentSpec.build());

            for (MethodSpec.Builder methodBuilder : methodBuilders) {
                tb.addMethod(methodBuilder.build());
            }

            JavaFile javaFile = JavaFile.builder(modelName + PATH_DI_COMPONENT, tb.build()).build();// 生成源代码
            javaFile.writeTo(mAbstractProcessor.mFiler);// 在 app module/build/generated/source/apt 生成一份源代码
        } catch (FilerException e) {
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fragmentModuleProcess() {
        try {

            if (modelName.isEmpty()) return;

            //创建一个接口
            TypeSpec.Builder tb = TypeSpec.classBuilder("FragmentModule").addModifiers(PUBLIC).addJavadoc("@ API工厂 此类由apt自动生成");
            //添加注解Module
            tb.addAnnotation(ClassName.get(PATH_DAGGER, "Module"));
            //            private Fragment mFragment;

            tb.addField(getFieldBuilder("android.support.v4.app", "Fragment","mFragment",PRIVATE));

//            public FragmentModule(Fragment fragment) {
//                mFragment = fragment;
//            }


            MethodSpec.Builder activityModuleBuilder
                    = MethodSpec.constructorBuilder()
                    .addJavadoc("@此方法由apt自动生成")
                    .addModifiers(PUBLIC)
                    .addParameter(ClassName.get("android.support.v4.app", "Fragment"), "fragment")
                    .addStatement("this.mFragment = fragment");
            tb.addMethod(activityModuleBuilder.build());


//            @Provides
//            @FragmentScope
//            public Context provideContext() {
//                return mFragment.getActivity();
//            }
//

            MethodSpec.Builder provideContextBuilder
                    = MethodSpec.methodBuilder("provideContext")
                    .addJavadoc("@此方法由apt自动生成")
                    .addModifiers(PUBLIC)
                    .addAnnotation(ClassName.get(PATH_DAGGER, "Provides"))
                    .addAnnotation(ClassName.get(PATH_BASE_LIB + PATH_DI_SCOPE, "FragmentScope"))
                    .addStatement("return mFragment.getActivity()")
                    .returns(ClassName.get(PATH_ANDROID_CONTENT, "Context"));
            tb.addMethod(provideContextBuilder.build());

//            @Provides
//            @FragmentScope
//            public Activity provideActivity() {
//                return mFragment.getActivity();
//            }

            MethodSpec.Builder provideActivityBuilder
                    = MethodSpec.methodBuilder("provideActivity")
                    .addJavadoc("@此方法由apt自动生成")
                    .addModifiers(PUBLIC)
                    .addAnnotation(ClassName.get(PATH_DAGGER, "Provides"))
                    .addAnnotation(ClassName.get(PATH_BASE_LIB + PATH_DI_SCOPE, "FragmentScope"))
                    .addStatement("return mFragment.getActivity()")
                    .returns(ClassName.get(PATH_ANDROID_APP, "Activity"));
            tb.addMethod(provideActivityBuilder.build());
//
//            @Provides
//            @FragmentScope
//            public Fragment provideFragment() {
//                return mFragment;
//            }

            MethodSpec.Builder provideFragmentBuilder
                    = MethodSpec.methodBuilder("provideFragment")
                    .addJavadoc("@此方法由apt自动生成")
                    .addModifiers(PUBLIC)
                    .addAnnotation(ClassName.get(PATH_DAGGER, "Provides"))
                    .addAnnotation(ClassName.get(PATH_BASE_LIB + PATH_DI_SCOPE, "FragmentScope"))
                    .addStatement("return mFragment")
                    .returns(ClassName.get("android.support.v4.app", "Fragment"));
            tb.addMethod(provideFragmentBuilder.build());

            JavaFile javaFile = JavaFile.builder(modelName + PATH_DI_MODULE, tb.build()).build();// 生成源代码
            javaFile.writeTo(mAbstractProcessor.mFiler);// 在 app module/build/generated/source/apt 生成一份源代码
        } catch (FilerException e) {
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void compinentFragmentProcess() {
        try {

            if (modelName.isEmpty()) return;
            //创建一个接口
            TypeSpec.Builder tb
                    = TypeSpec.classBuilder("ComponentFragment")
                    .addTypeVariable(TypeVariableName.get("P", ClassName.get(PATH_BASE_LIB + PATH_MVP, "BasePresenter")))
                    .addModifiers(PUBLIC, ABSTRACT)
                    .addJavadoc("@ API工厂 此类由apt自动生成");
            tb.superclass(ParameterizedTypeName.get(ClassName.get(PATH_BASE_LIB + PATH_BASE + PATH_FRAGMENT, "MvpFragment"), TypeVariableName.get("P")));


//            protected BaseApplication mBaseApplication;
            tb.addField(getFieldBuilder(PATH_BASE_LIB + PATH_BASE,"BaseApplication","mBaseApplication",PROTECTED));
//            protected ActivityComponent mActivityComponent;
            tb.addField(getFieldBuilder(modelName + PATH_DI_COMPONENT,"FragmentComponent","mFragmentComponent",PROTECTED));

//            @Override
//            public void setupFragmentComponent(AppComponent appComponent) {
//                mBaseApplication = (BaseApplication)mActivity.getApplication();
//        mFragmentComponent = DaggerFragmentComponent
//                .builder()
//                .appComponent(appComponent)
//                .fragmentModule(new FragmentModule(this))
//                .build();
//                initInject(mFragmentComponent);
//            }

            MethodSpec.Builder setupFragmentComponentBuilder
                    = MethodSpec.methodBuilder("setupFragmentComponent")
                    .addJavadoc("@此方法由apt自动生成")
                    .addModifiers(PUBLIC)
                    .addAnnotation(ClassName.get("java.lang", "Override"))
                    .addParameter(ClassName.get(PATH_BASE_LIB + PATH_DI_COMPONENT,"AppComponent"),"appComponent")
                    .addStatement("mBaseApplication = (BaseApplication)mActivity.getApplication()")
                    .addStatement("mFragmentComponent = $T\n" +
                                    ".builder()\n" +
                                    ".appComponent(appComponent)\n" +
                                    ".fragmentModule(new $T(this))\n" +
                                    ".build()"
                            ,ClassName.get(modelName + PATH_DI_COMPONENT,"DaggerFragmentComponent")
                            ,ClassName.get(modelName + PATH_DI_MODULE,"FragmentModule"))
                    .addStatement("initInject(mFragmentComponent)");
            tb.addMethod(setupFragmentComponentBuilder.build());

//            //提供AppComponent(提供所有的单例对象)给子类，进行Component依赖
//             protected abstract void initInject(FragmentComponent mFragmentComponent);
            MethodSpec.Builder initInjectBuilder
                    = MethodSpec.methodBuilder("initInject")
                    .addJavadoc("@此方法由apt自动生成\n")
                    .addModifiers(PROTECTED,ABSTRACT)
                    .addParameter(ClassName.get(modelName + PATH_DI_COMPONENT,"FragmentComponent"),"fragmentComponent");
            tb.addMethod(initInjectBuilder.build());

            JavaFile javaFile = JavaFile.builder(modelName + ".base", tb.build()).build();// 生成源代码
            javaFile.writeTo(mAbstractProcessor.mFiler);// 在 app module/build/generated/source/apt 生成一份源代码
        } catch (FilerException e) {
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getModelName(TypeElement element) {

        String[] split = ClassName.get(element).packageName().split("\\.");

        if (split.length >= 3) {
            return split[0] + "." + split[1] + "." + split[2];
        } else {
            return ClassName.get(element).packageName();
        }

    }

    private FieldSpec getFieldBuilder(String packageName, String className, String fieldName, Modifier... modifiers) {

        return FieldSpec.builder(ClassName.get(packageName, className), fieldName)
                .addJavadoc("@此属性由apt自动生成")
                .addModifiers(modifiers)
                .build();
    }
}
