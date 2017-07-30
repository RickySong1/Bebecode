package questions;

import java.util.ArrayList;

/**
 * Created by SuperSong on 2017-03-03.
 */

public class Month27_29 extends MonthQuestions{
    private ArrayList<String[]> questionList;
    private ArrayList<int[]> answerList;
    private  ArrayList<Integer> teacherImportantPosition;

    public ArrayList<String[]> getQuestionList(){
        return questionList;
    }
    public ArrayList<int[]> getAnswerList(){
        return answerList;
    }
    public ArrayList<Integer> getTeacherImportantPosition() { return teacherImportantPosition;}

    public Month27_29(){

        answerList = new ArrayList<int[]>();
        answerList.add(new int[]{10,17,23});  // big-muscle
        answerList.add(new int[]{12,16,21});  // small-muscle
        answerList.add(new int[]{10,17,23});  // recognition
        answerList.add(new int[]{8,15,23});   // language
        answerList.add(new int[]{8,15,23});  // social skill
        answerList.add(new int[]{9,13,22});  // self

        teacherImportantPosition = new ArrayList<>();
        // social problem
        teacherImportantPosition.add(32); teacherImportantPosition.add(33); teacherImportantPosition.add(34); teacherImportantPosition.add(35); teacherImportantPosition.add(36); teacherImportantPosition.add(37); teacherImportantPosition.add(38); teacherImportantPosition.add(39);

        questionList = new ArrayList<String[]>();

        // big muscle
        questionList.add(new String[]{
                "제자리에서 양발을 모아 깡충 뛴다.",
                "계단의 가장 낮은 층에서 두발을 모아 바닥으로 뛰어내린다.",
                "서있는 자세에서 머리 위로 팔을 높이 들어 공을 앞으로 던진다.",
                "난간을 붙잡고 한발씩 번갈아 내딛으며 계단을 올라간다.",
                "발뒤꿈치를 들고 발끝으로 네 걸음 이상 걷는다.",
                "난간을 붙잡지 않고 한 계단에 양발을 모으고 한발씩 계단을 올라간다.",
                "아무 것도 붙잡지 않고 한발로 1초간 서 있는다.",
                "아무 것도 붙잡지 않고 한 계단에 양발을 모으고 한발씩 계단을 내려간다."
        });

        // small muscle
        questionList.add(new String[]{
                "블록 2개 이상을 옆으로 나란히 배열한다.",
                "문손잡이를 돌려서 연다.",
                "연필의 아랫부분을 잡는다.",
                "유아용 가위를 주면 실제로 종이를 자르지는 못해도 한 손으로 종이를 잡고 다른 손으로는 가위 날을 벌리고 오므리며 종이를 자르려고 시도한다.",
                "신발 끈 구멍이나 구슬구멍에 끈을 끼운 후 빼낸다.",
                "수평선 그리는 시범을 보여주면 흉내 내서 그린다(이미 그려져 있는 선 위에 따라 그리는 것은 해당되지 않는다.",
                "엄지손가락과 다른 손가락 사이로 연필, 크레용 또는 펜 등을 잡는다.",
                "자신의 옷이나 인형 옷의 단추를 푼다."
        });

        // recognition
        questionList.add(new String[]{
                "지시에 따라 신체부위 5개 이상을 가리킨다(예: 눈, 코, 입, 귀, 팔)",
                "2개의 물건 중에서 큰 것과 작은 것을 구분한다.",
                "빨간, 노란, 파란 토막들을 섞어 놓으면 같은 색의 토막들끼리 분류한다.",
                "동그라미, 네모, 세모와 같이 간단한 도형 맞추기 판에 3조각 이상 맞춘다.",
                "'많다-적다'와 같은 양의 개념을 이해한다(예: 사탕 2개와 사탕6개를 놓고 어떤 것이 더 많은지 물었을 때 많은 것을 가리킬 수 있다)",
                "'하나'라는 개념을 이해한다(예: 사탕 3개를 책상 위에 놓고 '한개 주세요'라고 하면 한 개를 줄 수 있다)",
                "6조각으로 된 퍼즐을 맞춘다.",
                "2개의 선 중에서 길이가 긴 것과 짧은 것을 구분한다"
        });

        //language
        questionList.add(new String[]{
                "다른 의미를 가진 두 개의 단어를 붙여 말한다(예: '엄마 우유', '장난감 줘', '과자 먹어')",
                "단어의 끝 억양을 높임으로써 질문의 형태로 말한다.",
                "자기 물건에 대해 '내 것'이란 표현을 한다.",
                "손으로 가리키거나 동작으로 힌트를 주지 않아도, '식탁 위에 컵을 놓으세요'라고 말하면 아이가 정확하게 행동한다.",
                "'안에', '위에', '밑에', '뒤에' 중 2가지 이상을 이해한다.",
                "그림책을 볼 때, 그림에서 일어나는 상황이나 행동을 말한다(예: 아이에게 '멍멍이가 뭘하고 있지요?' 라고 물으면 '잔다','먹는다','운다'등 책에 나와 있는 상황을 말한다).",
                "'이름이 뭐예요?'하고 물으면, 성과 이름을 모두 말한다.",
                "간단한 대화를 주고 받는다."
        });

        // social skills
        questionList.add(new String[]{
                "아이가 엄마(보호자)의 관심을 끌기 위해, 주변의 물건들이나 멀리 있는 사물을 손가락으로 가리킨다.",
                "하고 있는 것을 못하게 하면 '싫어'라고 말한다.",
                "엄마(보호자)의 관심을 끌기 위해 흥미 있는 물건이나 자신이 만든 것, 그린 것 등을 가져다 보여 준다.",
                "어른이 시키면 '미안해', '고마워'라는 말을 한다.",
                "다른 아이들의 행동을 보고 (간단한) 놀이의 규칙을 따른다.",
                "자신의 기분을 말로 표현한다(기분이 좋으면 좋다고 말하고, 나쁘면 나쁘다고 말한다)",
                "자기 손에 닿지 않는 물건을 다른 사람에게 건네 달라고 부탁한다(예: 물 좀 주세요)",
                "어른이 이끄는 집단 놀이에서 규칙을 따른다."
        });

        // self
        questionList.add(new String[]{
                "어른이 코를 닦으라고 말해주면, 휴지로 코를 닦는다.",
                "단추나 끈을 풀어주면 바지를 혼자서 벗는다",
                "뾰족한 가구모퉁이나 난간 없는 층계 등 위험물을 피한다",
                "음식을 먹다 흘리면 손이나 옷으로 닦지 않고 스스로 휴지나 냅킨으로 닦는다.",
                "바지에 발끝을 약간만 넣어주면 허리까지 완전히 끌어올린다.",
                "낮 동안 소변을 가린다.",
                "낮 동안 대변을 가린다.",
                "물을 틀어주거나 받아주면 혼자서 비누로 손을 씻는다."
        });

        questionList.add(new String[]{
                "걷지 못한다.",
                "의미 있는 단어를 말하지 못한다",
                "아이가 보호자와 이야기를 하거나 놀 때 눈을 맞추지 않는다",
                "청력이 정상임에도 불구하고 이름을 불러도 쳐다보지 않는다",
                "어른들의 관심을 끄는 행동을 하지 않는다(멀리 있는 사물 가리키기, 물건을 가져다 보여주기, 같이 놀자고 건네주기, 소리내어 부르기, 손가락으로 가리키기 등)"
        });

    }
}
