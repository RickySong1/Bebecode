package questions;

import java.util.ArrayList;

/**
 * Created by SuperSong on 2017-03-03.
 */

public class Month30_32 extends MonthQuestions{
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
    private String filename;

    public String getFileName() {
        return filename;
    }
    public Month30_32(String file){

        filename = file;

        answerList = new ArrayList<int[]>();
        answerList.add(new int[]{14,17,23});  // big-muscle
        answerList.add(new int[]{12,15,22});  // small-muscle
        answerList.add(new int[]{7,14,23});  // recognition
        answerList.add(new int[]{12,19,23});   // language
        answerList.add(new int[]{9,15,23});  // social skill
        answerList.add(new int[]{11,15,23});  // self

        teacherImportantPosition = new ArrayList<>();
        // social problem
        teacherImportantPosition.add(32); teacherImportantPosition.add(33); teacherImportantPosition.add(34); teacherImportantPosition.add(35); teacherImportantPosition.add(36); teacherImportantPosition.add(37); teacherImportantPosition.add(38); teacherImportantPosition.add(39);

        questionList = new ArrayList<String[]>();

        // big muscle
        questionList.add(new String[]{
                "계단의 가장 낮은 층에서 두발을 모아 바닥으로 뛰어내린다.",
                "서있는 자세에서 머리 위로 팔을 높이 들어 공을 앞으로 던진다.",
                "발뒤꿈치를 들고 발끝으로 네 걸음 이상 걷는다.",
                "난간을 붙잡지 않고 한 계단에 양발을 모으고 한발씩 계단을 올라간다.",
                "아무 것도 붙잡지 않고 한발로 1초간 서 있는다.",
                "아무 것도 붙잡지 않고 한 계단에 양발을 모으고 한발씩 계단을 내려간다.",
                "아무 것도 붙잡지 않고 한발씩 번갈아 내딛으며 계단을 올라간다.",
                "큰 공을 던져주면 양파과 가슴을 이용해 받는다."
        });

        // small muscle
        questionList.add(new String[]{
                "문손잡이를 돌려서 연다.",
                "연필의 아랫부분을 잡는다.",
                "유아용 가위를 주면 실제로 종이를 자르지는 못해도 한 손으로 종이를 잡고 다른 손으로는 가위 날을 벌리고 오므리며 종이를 자르려고 시도한다.",
                "신발 끈 구멍이나 구슬구멍에 끈을 끼운 후 빼낸다.",
                "수평선 그리는 시범을 보여주면 흉내 내서 그린다(이미 그려져 있는 선 위에 따라 그리는 것은 해당되지 않는다.",
                "엄지손가락과 다른 손가락 사이로 연필, 크레용 또는 펜 등을 잡는다.",
                "자신의 옷이나 인형 옷의 단추를 푼다.",
                "원이 그려진 것을 보여주면 원을 그린다 (그리는 과정의 시범을 보지 않고도 그려야 한다)."
        });

        // recognition
        questionList.add(new String[]{
                "빨간, 노란, 파란 토막들을 섞어 놓으면 같은 색의 토막들끼리 분류한다.",
                "'많다-적다'와 같은 '양'의 개념을 이해한다 (예: 사탕 2개와 사탕 6개를 놓고 어떤 것이 더 많은지 물었을 때 많은 것을 가리킬 수 있다).",
                "6조각으로 된 퍼즐을 맞춘다.",
                "2개의 선 중에서 길이가 긴 것과 짧은 것을 구분한다",
                "'둘'이라는 개념을 이해한다 (예: 사탕 3개를 책상 위에 놓고 '두개 주세요'라고 하면 두 개를 줄 수 있다).",
                "크기가 다른 3개의 사물을 놓고, '가장 큰 것', '중간 크기의 것', '가장 작은 것'을 구분한다.",
                "'안, 밖, 사이'와 같은 공간에 대한 개념을 이해한다('컵을 상자 안에 넣어'라고 시키면 그대로 따라할 수 있다).",
                "연관성이 없는 두 가지 지시사항을 시키면 2가지를 순서대로 기억하여 수행한다 (예: 휴지 버리고 책 가지고 와)."
        });

        //language
        questionList.add(new String[]{
                "손으로 가리키거나 동작으로 힌트를 주지 않아도, '식탁 위에 컵을 놓으세요'라고 말하면 아이가 정확하게 행동한다.",
                "'안에', '위에', '밑에', '뒤에' 중 2가지 이상을 이해한다.",
                "그림책을 볼 때, 그림에서 일어나는 상황이나 행동을 말한다(예: 아이에게 '멍멍이가 뭘하고 있지요?' 라고 물으면 '잔다','먹는다','운다'등 책에 나와 있는 상황을 말한다).",
                "'이름이 뭐예요?'하고 물으면, 성과 이름을 모두 말한다.",
                "'~했어요'와 같이 과거형으로 말한다.",
                "간단한 대화를 주고 받는다.",
                "'예쁘다' 또는 '무섭다'의 뜻을 안다.",
                "할아버지, 할머니, 오빠(형), 누나(언니), 동생과 같은 호칭을 정확하게 사용한다."
        });

        // social skills
        questionList.add(new String[]{
                "어른이 시키면 '미안해', '고마워'라는 말을 한다.",
                "다른 아이들의 행동을 보고 (간단한) 놀이의 규칙을 따른다.",
                "자신의 기분을 말로 표현한다(기분이 좋으면 좋다고 말하고, 나쁘면 나쁘다고 말한다)",
                "3~4명과 어울려서 숨바꼭질, 술래잡기 등을 한다.",
                "어른이 이끄는 집단 놀이에서 규칙을 따른다.",
                "자기 차례를 기다린다 (예: 놀이터, 미끄럼틀)",
                "놀이 중에 도움이 필요한 친구를 도와주고 달래준다.",
                "혼자서 혹은 또래와 함께 인형놀이(상상놀이)를 한다."
        });

        // self
        questionList.add(new String[]{
                "음식을 먹다 흘리면 손이나 옷으로 닦지 않고 스스로 휴지나 냅킨으로 닦는다.",
                "바지에 발끝을 약간만 넣어주면 허리까지 완전히 끌어올린다.",
                "낮 동안 소변을 가린다.",
                "낮 동안 대변을 가린다.",
                "물을 틀어주거나 받아주면 혼자서 비누로 손을 씻는다.",
                "양말을 혼자서 신는다.",
                "도와주지 않아도 혼자서 밥을 먹는다.",
                "단추를 풀러주면 셔츠나 내의를 벗는다."
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
