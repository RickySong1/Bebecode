package questions;

import java.util.ArrayList;

/**
 * Created by SuperSong on 2017-03-03.
 */

public class Month36_41 extends MonthQuestions{
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
    public Month36_41(String _file){
        filename = _file;

        answerList = new ArrayList<int[]>();
        answerList.add(new int[]{11,15,22});  // big-muscle
        answerList.add(new int[]{10,15,22});  // small-muscle
        answerList.add(new int[]{10,16,22});  // recognition
        answerList.add(new int[]{10,18,23});   // language
        answerList.add(new int[]{10,15,23});  // social skill
        answerList.add(new int[]{8,15,22});  // self

        teacherImportantPosition = new ArrayList<>();
        // social problem
        teacherImportantPosition.add(32); teacherImportantPosition.add(33); teacherImportantPosition.add(34); teacherImportantPosition.add(35); teacherImportantPosition.add(36); teacherImportantPosition.add(37); teacherImportantPosition.add(38); teacherImportantPosition.add(39);

        questionList = new ArrayList<String[]>();

        // big muscle
        questionList.add(new String[]{
                "아무 것도 붙잡지 않고 한발씩 번갈아 내딛으며 계단을 올라간다.",
                "큰 공을 던져주면 양파과 가슴을 이용해 받는다.",
                "세발자전거의 페달을 밟아서 앞으로 나갈 수 있다.",
                "선을 따라 똑바로 앞으로 걷는다.",
                "제자리에서 두발을 모아 멀리뛰기를 한다.",
                "아무 것도 붙잡지 않고 한 발로 3초 이상 서 있는다.",
                "보조바퀴가 있는 두발 자전거를 탈 수 있다.",
                "한발로 2~3 발자국 뛴다."
        });

        // small muscle
        questionList.add(new String[]{
                "자신의 옷이나 인형 옷의 단추를 푼다.",
                "원이 그려진 것을 보여주면 원을 그린다 (그리는 과정의 시범을 보지 않고도 그려야 한다).",
                "종이를 두 번 연달아 접는다(접은 선은 정확하지 않아도 된다).",
                "그려진 점선을 따라 선을 그린다.",
                "사각형이 그려진 것을 보여주면 사각형을 그린다 (그리는 과정의 시범을 보지 않고도 그려야 한다. 또한 각이 교차되도록 그리는 것은 괜찮지만, 둥글거나 좁은 각으로 그리는 것은 해당되지 않는다).",
                "가위로 직선을 따라 똑바로 오린다.",
                "뚜껑을 돌려서 연다.",
                "십자(+) 그리는 시범을 보여주면 흉내 내서 그린다 (이미 그려져 있는 선 위에 따라 그리는 것은 해당되지 않는다)."
        });

        // recognition
        questionList.add(new String[]{
                "연관성이 없는 두 가지 지시사항을 시키면 2가지를 순서대로 기억하여 수행한다 (예: 휴지 버리고 책 가지고 와).",
                "자신의 성별을 안다.",
                "'셋'이라는 개념을 이해한다 (예: 사탕 여러 개를 책상 위에 놓고 '세 개 주세요'라고 하면 세 개를 줄 수 있다).",
                "과일, 탈 것, 가구가 그려진 그림카드를 섞어 놓았을 때, 아이가 같은 종류끼리 분류한다.",
                "'가장 많은, 가장 적은'의 개념을 모두 이해한다.",
                "다른 사람이 한 말을 전달한다.",
                "5가지 이상의 색깔을 정확하게 구분한다.",
                "사람 (예: 엄마, 아빠)를 그리라고 하면 신체의 3부분 이상을 그린다."
        });

        //language
        questionList.add(new String[]{
                "'이름이 뭐예요?'하고 물으면, 성과 이름을 모두 말한다.",
                "다른 의미를 가진 네 단어 이상을 연결하여 문장을 말한다(예: '장난감 사러 가게에 가요').",
                "'~했어요'와 같이 과거형으로 말한다.",
                "간단한 대화를 주고 받는다.",
                "완전한 문장으로 이야기한다 (예: '멍멍이가 까까를 먹었어').",
                "'-은, -는, -이, -가'와 같은 조사를 적절히 사용하여 문장을 완성한다 (예: '고양이는 야옹하고 울어요', '친구가 좋아요').",
                "같은 분류에 속한 것을 적어도 세 가지 이상 말한다(예: 동물을 말하도록 시키면, '강아지, 고양이, 코끼리'와 같이 말한다).",
                "'~할 거예요', '~하고 싶어요'와 같이 미래에 일어날 일을 상황에 맞게 표현한다."
        });

        // social skills
        questionList.add(new String[]{
                "어른이 이끄는 집단 놀이에서 규칙을 따른다.",
                "자기 차례를 기다린다 (예: 놀이터, 미끄럼틀)",
                "놀이 중에 도움이 필요한 친구를 도와주고 달래준다.",
                "혼자서 혹은 또래와 함께 인형놀이(상상놀이)를 한다.",
                "다른 사람에게 간단한 놀이의 규칙을 설명한다.",
                "다른 아이들과 있을 때, 차례를 지키고 놀잇감을 나누면서 논다.",
                "다른 아이들의 행동에 대해 이야기한다 (예: '민수가 나 때렸어')",
                "소꿉놀이, 가게놀이, 학교놀이, 병원놀이등 여럿이 함께 어울려 하는 놀이를 한다."
        });

        // self
        questionList.add(new String[]{
                "양말을 혼자서 신는다.",
                "도와주지 않아도 혼자서 밥을 먹는다.",
                "단추를 풀러주면 셔츠나 내의를 벗는다.",
                "장화를 혼자서 신는다.",
                "옷의 앞과 뒤를 구분하여 입는다.",
                "혼자서 티셔츠를 입는다.",
                "외투의 큰 단추를 낀다.",
                "혼자서 손을 깨끗이 싯고 수건으로 닦는다."
        });

        questionList.add(new String[]{
                "의미 있는 단어를 말하지 못한다",
                "두 단어를 결합하는 말을 하지 못한다.",
                "아이가 보호자와 이야기를 하거나 놀 때 눈을 맞추지 않는다",
                "청력이 정상임에도 불구하고 이름을 불러도 쳐다보지 않는다",
                "어른들의 관심을 끄는 행동을 하지 않는다(멀리 있는 사물 가리키기, 물건을 가져다 보여주기, 같이 놀자고 건네주기, 소리내어 부르기, 손가락으로 가리키기 등)",
                "또래 아이들에게 관심이 없다. 또래와 함께 있어도 아이들을 지켜보거나, 행동을 따라 하거나, 함께 놀려고 시도하지 않는다."
        });

    }
}
