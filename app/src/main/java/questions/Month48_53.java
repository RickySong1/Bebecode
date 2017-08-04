package questions;

import java.util.ArrayList;

/**
 * Created by SuperSong on 2017-03-03.
 */

public class Month48_53 extends MonthQuestions{
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

    public Month48_53(String _file){

        filename = _file;

        answerList = new ArrayList<int[]>();
        answerList.add(new int[]{13,18,23});  // big-muscle
        answerList.add(new int[]{11,17,23});  // small-muscle
        answerList.add(new int[]{10,17,22});  // recognition
        answerList.add(new int[]{11,16,23});   // language
        answerList.add(new int[]{9,14,22});  // social skill
        answerList.add(new int[]{11,17,23});  // self

        teacherImportantPosition = new ArrayList<>();
        // social problem
        teacherImportantPosition.add(32); teacherImportantPosition.add(33); teacherImportantPosition.add(34); teacherImportantPosition.add(35); teacherImportantPosition.add(36); teacherImportantPosition.add(37); teacherImportantPosition.add(38); teacherImportantPosition.add(39);

        questionList = new ArrayList<String[]>();

        // big muscle
        questionList.add(new String[]{
                "아무 것도 붙잡지 않고 한 발로 3초 이상 서 있는다.",
                "보조바퀴가 있는 두발 자전거를 탈 수 있다.",
                "한발로 2~3 발자국 뛴다.",
                "서 있는 자세에서 머리 위로 팔을 높이 들어 공을 2미터 이상 앞으로 멀리 던진다.",
                "아무것도 붙잡지 않고 한발씩 벌갈아 내딛으며 계단을 내려간다.",
                "굴러가는 공을 발로 세운다.",
                "2미터 거리에서 테니스 공 크기의 공을 던지면 두 손으로 잡는다.",
                "공을 바닥에 한 번 튕길 수 있다."
        });

        // small muscle
        questionList.add(new String[]{
                "사각형이 그려진 것을 보여주면 사각형을 그린다 (그리는 과정의 시범을 보지 않고도 그려야 한다. 또한 각이 교차되도록 그리는 것은 괜찮지만, 둥글거나 좁은 각으로 그리는 것은 해당되지 않는다).",
                "가위로 직선을 따라 똑바로 오린다.",
                "십자(+) 그리는 시범을 보여주면 흉내 내서 그린다 (이미 그려져 있는 선 위에 따라 그리는 것은 해당되지 않는다).",
                "계단 모양을 블록으로 쌓는다.",
                "색칠 공부의 그림 속에 색을 칠한다.",
                "네모를 가위로 오린다.",
                "피라미드 모양을 블록으로 쌓는다.",
                "엄지 손가락과 다른 네 손가락을 차례로 맞닿게 한다."
        });

        // recognition
        questionList.add(new String[]{
                "과일, 탈 것, 가구가 그려진 그림카드를 섞어 놓았을 때, 아이가 같은 종류끼리 분류한다.",
                "'가장 많은, 가장 적은'의 개념을 모두 이해한다.",
                "다른 사람이 한 말을 전달한다.",
                "물건을 하나씩 열(10)까지 센다.",
                "아침, 점심, 저녁, 오늘, 내일 등 시간의 개념을 이해한다.",
                "사람(예: 엄마,아빠)을 그리라고 하면 신체의 3부분 이상을 그린다.",
                "손에 닿지 않는 물건을 도구로 사용하여 가져온다(예: 책상 밑에 들어간 것을 막대기로 꺼낸다).",
                "자신이 원하는 TV 채널을 찾는다."
        });

        //language
        questionList.add(new String[]{
                "'-은, -는, -이, -가'와 같은 조사를 적절히 사용하여 문장을 완성한다 (예: '고양이는 야옹하고 울어요', '친구가 좋아요').",
                "같은 분류에 속한 것을 적어도 세 가지 이상 말한다(예: 동물을 말하도록 시키면, '강아지, 고양이, 코끼리'와 같이 말한다).",
                "'~할 거예요', '~하고 싶어요'와 같이 미래에 일어날 일을 상황에 맞게 표현한다.",
                "그날 있었던 일을 이야기한다.",
                "친숙한 단어의 반대말을 말한다. (예: 덥다<->춥다, 크다<->작다)",
                "간단한 농담이나 빗대어 하는 말의 뜻을 알아차린다.",
                "단어의 뜻을 물어보면 설명한다(예: '신발이 뭐야?' 라는 질문에 '밖에 나갈 때 신는 거요'와 같은 대답을 할 수 있다).",
                "가족 이외의 사람도 이해할 수 있을 정도로 모든 단어의 발음이 정확하다."
        });

        // social skills
        questionList.add(new String[]{
                "다른 사람에게 간단한 놀이의 규칙을 설명한다.",
                "다른 아이들과 있을 때, 차례를 지키고 놀잇감을 나누면서 논다.",
                "소꿉놀이, 가게놀이, 학교놀이, 병원놀이등 여럿이 함께 어울려 하는 놀이를 한다.",
                "자기보다 어린 아동을 돌봐 주는 행동을 한다.",
                "가위바위보로 승부를 정한다.",
                "처음 만난 또래와 쉽게 어울린다.",
                "또래와 함께 차례나 규칙을 알아야 할 수 있는 놀이를 한다(예: 윷놀이, 보드게임).",
                "자기 생각을 이야기하고 다른 아이의 말을 귀 기울여 듣는다."
        });

        // self
        questionList.add(new String[]{
                "벙어리장갑의 좌우를 구분하여 손에 낀다.",
                "혼자서 손을 깨끗이 씻고 수건으로 닦는다.",
                "식사 할 때 젓가락을 사용한다 (연습용 젓가락도 포함).",
                "혼자서 비누칠을 하여 손과 얼굴을 씻고 수건으로 닦는다.",
                "대소변을 볼 때 혼자서 옷을 벗고 입는다.",
                "밤에 자는 동안 대소변을 가린다.",
                "대소변을 본 후 화장실 물을 내린다.",
                "숟가락 등을 사용하여 빵에 버터나 잼을 바른다."
        });

        questionList.add(new String[]{
                "아이가 보호자와 이야기를 하거나 놀 때 눈을 맞추지 않는다",
                "청력이 정상임에도 불구하고 이름을 불러도 쳐다보지 않는다",
                "어른들의 관심을 끄는 행동을 하지 않는다(멀리 있는 사물 가리키기, 물건을 가져다 보여주기, 같이 놀자고 건네주기, 소리내어 부르기, 손가락으로 가리키기 등)",
                "또래 아이들에게 관심이 없다. 또래와 함께 있어도 아이들을 지켜보거나, 행동을 따라 하거나, 함께 놀려고 시도하지 않는다.",
                "간단한 규칙이 있으면서 편을 나누어서 하는 놀이 (예: 간단한 숨바꼭질, 잡기놀이, 쎄쎄쎄등)와 인형을 가지고 하는 상상놀이 (예: 인형에게 음식을 주기, 재우기, 로봇끼리 싸우기, 차를 타고 가기 등)를 전혀 할 줄 모른다."
        });
    }
}
