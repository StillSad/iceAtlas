package com.thinkwage.visiontestbundle.model;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ICE on 2017/7/21.
 */

public class VisionTest {

    public static String leftEyeGrades = "5.0";
    public static String rightEyeGrades = "5.0";

    public static final String RESULT_PASS = "result_pass";
    public static final String RESULT_END = "result_end";
    public static final String RESULT_TESTING = "result_testing";
    //方向正则
    public static String upReg = ".*[唱方勷禳瓤穣穰儴躟让鬤蘘嚷獽攘瀼壌壤懹爙晌禓伤螪緔觞觴鞝蔏商丄上滳漡绱垧墒尚尙恦慯赏殇殤熵].*";
    public static String downReg = ".*[枭枵效敩斆歊晓暁曉揱殽毊肖膮皛皢穘销痚痟鸮侾硝硣窙綃傚翛笑筱筿篠簘詨誟誵謏踃銷霄颵髇髐魈﨧萧箫蕭藃逍崤訤庨呺哓咲哮啸嘐嘨嘋嘵嘯骁宵宯婋猇獢撨洨消涍淆郩潇瀟绡小恔憢孝烋灱灲焇熽効栙勨橡乡欀晑曏祥瓨珦瓖想相佭缿絴緗纕襄像翔箱象詳跭享亯详香項芗项葙萫厢忀蚃庠廂向响饷湘缃柙梺暇祫瑕侠睱瞎疜俠硖硤蝦縖傄翈舺筪轄赮谺閕閜霞黠蕸遐峽磍匣狎狭狹峡下陜圷埉夏辖炠烚煆].*";
    public static String leftReg = ".*[罗说杼柱柷株槠樦櫧祩殶珠伫住佇铢铸侏竚眝瞩疰瘃砫硃罜窋袾主蛀蛛紵紸絑翥竹竺笁笜筑筯箸築篴貯誅諸跓跦诛诸麈麆鮢鯺苎茱茿莇著蓫迬逐逫秼詝註驻宔猪嵀壴拄拀邾注洙渚陼潴濐瀦纻坾墸贮煮炷炢烛煑燝燭劯朱昨祚胙作佐稓穝侳袏做筰鈼飵莋葃葄秨左座咗唑岞岝阼捽坐怍].*";
    public static String rightReg = ".*[永用又右呦栯梄楢槱牰牖牗攸猷祐禉优怣悠憂有肬佑铀铕侑疣由甴蚰蚴蜏蝣羑釉輏輶誘酉酭貁鈾亴銪鱿鲉诱麀友魷鮋黝鼬苃莜莸莠卣蕕迶逌逰遊峳秞訧庮哊唀嚘宥姷犹狖猶峟邮沋油泑浟游郵湵滺瀀囿忧怞怮懮幼幽尤孧丣].*";

    //全部测试数据
    private ArrayList<VisionTestGrades> visionTest;
    //当前等级的测试数据
    private VisionTestGrades visionTestGrades;
    //当前测试等级
    private int testGrades = 0;
    private int oldDirectionIndex = -1;

    Random random;

    {
        random = new Random();
    }

    public VisionTest() {

        visionTest = new ArrayList<>();
        visionTestGrades = new VisionTestGrades(testGrades);
        visionTest.add(visionTestGrades);
    }

    //添加一次测试
    public String addTest() {
        String currentGradesTestResult = getCurrentGradesTestResult();
        //随机生成方向
        int directionIndex = random.nextInt(4);
        //新方向与上次方向不同
        while (oldDirectionIndex == directionIndex) {
            directionIndex = random.nextInt(4);
        }

        oldDirectionIndex = directionIndex;
        //获取当前测试等级结果
        switch (currentGradesTestResult) {
            case RESULT_END:
                //测试失败，结束测试
                break;
            case RESULT_PASS:
                //通过
                //测试等级加1
                testGrades++;
                if (testGrades > VisionTestGrades.grades.length - 1) {
                    //达到最大等级
                    testGrades--;
                    return RESULT_END;
                }
                //保存当前等级测试数据到全部测试数据里
                visionTest.add(visionTestGrades);
                //创建新的等级测试
                visionTestGrades = new VisionTestGrades(testGrades);

                visionTestGrades.addTest(oldDirectionIndex);
                break;
            case RESULT_TESTING:
                //测试中
                visionTestGrades.addTest(oldDirectionIndex);
                break;
        }

        return currentGradesTestResult;
    }

    //获取当前测试等级结果
    public String getCurrentGradesTestResult() {
        return visionTestGrades.getGradesTestResult();
    }

    //设置当前测试的结果
    public boolean setCurrentTestResult(String result) {
        return visionTestGrades.setCurrentTestResult(result);
    }

    //获取当前测试等级
    public float getTestGrades() {
        return visionTestGrades.getGrades(testGrades);
    }

    public int getCurrentTestGrades() {
        return testGrades;
    }

    public String getCurrentTestDirection() {
        return visionTestGrades.getCurrentTest().getDirection();
    }

    public int getCurrentTestDirectionIndex() {
        return visionTestGrades.getCurrentTest().getDirectionIndex();
    }

    //一个等级的测试
    public static class VisionTestGrades {


        private ArrayList<VisionTestItem> visionTestItems;
        private boolean gradesResult;

        public static final float[] grades = {4.0f, 4.1f, 4.2f, 4.3f, 4.4f, 4.5f, 4.6f, 4.7f, 4.8f, 4.9f, 5.0f, 5.1f, 5.2f, 5.3f};
        //测试等级索引（0——13）
        private int testGradesIndex;


        public VisionTestGrades(int testGradesIndex) {
            this.testGradesIndex = testGradesIndex;
            visionTestItems = new ArrayList<>();
        }

        //添加一次测试
        public void addTest(int directionIndex) {

            //在本组测试中添加一次测试
            visionTestItems.add(new VisionTestItem(directionIndex));


        }

        //获取等级测试的结果
        public String getGradesTestResult() {


            if (testGradesIndex >= 0 && testGradesIndex < 6) {
                //4.0～4.5各行视标中每行不能认错1个
                return calculationResult(1);
            } else if (testGradesIndex >= 6 && testGradesIndex < 11) {
                //4.6～5.0各行视标中每行不能认错2个
                return calculationResult(2);
            } else if (testGradesIndex >= 11 && testGradesIndex <= 14) {
                //5.1～5.3各行中每行不能认错3个。
                return calculationResult(3);
            }
            return RESULT_TESTING;
        }

        private String calculationResult(int count) {
            //统计正确测试正确数与失败数
            int successCount = 0;
            int failedCount = 0;
            for (VisionTestItem visionTestItem : visionTestItems) {
                if (visionTestItem.getItemResult()) {
                    successCount++;
                } else {
                    failedCount++;
                }
            }

            if (successCount >= count) {
                return RESULT_PASS;
            } else if (failedCount >= count) {
                return RESULT_END;
            }
            return RESULT_TESTING;
        }

        //获取当前测试
        public VisionTestItem getCurrentTest() {
            return visionTestItems.get(visionTestItems.size() - 1);
        }

        //设置当前测试的结果
        public boolean setCurrentTestResult(String result) {
            return getCurrentTest().setItemResult(result);
        }

        //获取当前测试结果
        public boolean getCurrentTestResult() {
            return getCurrentTest().getItemResult();
        }

        //获取上一次测试
        public VisionTestItem getLastTest() {
            int count = visionTestItems.size();
            if (count > 1) {
                return visionTestItems.get(count - 1);
            }
            return null;
        }

        public float getGrades(int testGrades) {
            if (testGrades >= 0 && testGrades <= 13) {
                return grades[testGrades];
            }
            return -1f;
        }

    }

    //一次测试
    public static class VisionTestItem {
        private static final String[] directions = {"左", "上", "右", "下"};
        //方向索引（0——3）
        private int directionIndex;
        //一次测试的结果
        private boolean itemResult;


        public VisionTestItem(int directionIndex) {
            this.directionIndex = directionIndex;
        }

        //获取测试方向索引
        public int getDirectionIndex() {
            return directionIndex;
        }

        //获取测试方向
        public String getDirection() {
            return directions[directionIndex];
        }

        public boolean getItemResult() {
            return itemResult;
        }

        public boolean setItemResult(String result) {
            if (TextUtils.isEmpty(result)) return false;
            this.itemResult = result.contains(directions[directionIndex]);
            return this.itemResult;
        }
    }
}
