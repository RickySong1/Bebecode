package questions;

import java.util.ArrayList;

/**
 * Created by SuperSong on 2017-03-03.
 */

public class Month60_65 extends MonthQuestions{
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

    public Month60_65(String _file){

        filename = _file;

        answerList = new ArrayList<int[]>();
        answerList.add(new int[]{12,18,23});  // big-muscle
        answerList.add(new int[]{12,20,23});  // small-muscle
        answerList.add(new int[]{11,16,22});  // recognition
        answerList.add(new int[]{11,17,23});   // language
        answerList.add(new int[]{10,14,21});  // social skill
        answerList.add(new int[]{13,16,22});  // self

        teacherImportantPosition = new ArrayList<>();
        // social problem
        teacherImportantPosition.add(32); teacherImportantPosition.add(33); teacherImportantPosition.add(34); teacherImportantPosition.add(35); teacherImportantPosition.add(36); teacherImportantPosition.add(37); teacherImportantPosition.add(38); teacherImportantPosition.add(39);

        questionList = new ArrayList<String[]>();

        // big muscle
        questionList.add(new String[]{
                "굴러가는 공을 발로 세운다.",
                "2미터 거리에서 테니스 공 크기의 공을 던지면 두 손으로 잡는다.",
                "공을 바닥에 한 번 튕길 수 있다.",
                "무릎 아래 높이로 매어져 있는 줄을 뛰어 넘을 수 있다.",
                "한발씩 번갈아 들고 뛴다.",
                "줄넘기를 1회 한다.",
                "두 손으로 한 발을 잡고, 세 발자국 이상 뛴다(닭 싸움 자세).",
                "굴러 가는 공을 발로 찰 수 있다."
        });

        // small muscle
        questionList.add(new String[]{
                "네모를 가위로 오린다.",
                "피라미드 모양을 블록으로 쌓는다.",
                "엄지 손가락과 다른 네 손가락을 차례로 맞닿게 한다.",
                "삼각형이 그려진 것을 보여주면 삼각형을 그린다(그리는 과정의 시범을 보지 않고도 그려야 한다).",
                "동그라미를 가위로 오린다.",
                "간단한 자동차 모양을 흉내내어 그린다.",
                "주전자나 물병의 물을 거의 흘리지 않고 컵에 붓는다.",
                "마름모가 그려진 것을 보여주면 마름모를 그린다(그리는 과정의 시범을 보지 않고도 그려야 한다)."
        });

        // recognition
        questionList.add(new String[]{
                "자신이 원하는 TV 채널을 찾는다.",
                "동화책을 읽어주면 내용의 일부를 말한다.",
                "엄마(보호자)가 많이 쓰는 물건들이 어떤 용도로 사용되는지 물으면 대답한다.",
                "1 더하기 2를 계산한다.",
                "자신의 왼쪽과 오른쪽을 구별하여 말한다.",
                "요일을 순서대로 말한다.",
                "100원보다 500원짜리 동전이 더 가치가 있다는 것을 안다.",
                "자기 생일을 말한다."
        });

        //language
        questionList.add(new String[]{
                "친숙한 단어의 반대말을 말한다. (예: 덥다<->춥다, 크다<->작다)",
                "간단한 농담이나 빗대어 하는 말의 뜻을 알아차린다.",
                "단어의 뜻을 물어보면 설명한다(예: '신발이 뭐야?' 라는 질문에 '밖에 나갈 때 신는 거요'와 같은 대답을 할 수 있다).",
                "'만약~라면 무슨 일이 일어날까?'와 같이 가상의 상황에 대한 질문에 대답한다(예: 동생이 있으면 어떨까?).",
                "이름이나 쉬운 단어 2-3개를 보고 읽는다",
                "가족 이외의 사람도 이해할 수 있을 정도로 모든 단어의 발음이 정확하다.",
                "끝말잇기를 한다.",
                "자기 이름이나 2-4개의 글자로 된 단어를 보지 않고 쓸 수 있다(예: 동생, 신호등, 대한민국)."
        });

        // social skills
        questionList.add(new String[]{
                "처음 만난 또래와 쉽게 어울린다.",
                "또래와 함께 차례나 규칙을 알아야 할 수 있는 놀이를 한다(예: 윷놀이, 보드게임).",
                "자기 생각을 이야기하고 다른 아이의 말을 귀 기울여 듣는다.",
                "게임을 하는 방법에 대해 다른 아이와 이야기를 나눈다.",
                "다른 아이들과 적극적으로 어울린다.",
                "시키지 않아도 아는 사람에게 '안녕하세요?' 라고 인사한다.",
                "친구를 집으로 불러서 함께 논다.",
                "친구나 가족에게 전화를 건다."
        });

        // self
        questionList.add(new String[]{
                "밤에 자는 동안 대소변을 가린다.",
                "대소변을 본 후 화장실 물을 내린다.",
                "숟가락 등을 사용하여 빵에 버터나 잼을 바른다.",
                "목욕한 후에 혼자서 몸을 수건으로 닦는다.",
                "웃옷의 지퍼를 혼자 끼워 올린다.",
                "옷이 더러워지면 스스로 갈아입는다.",
                "우유 종이팩을 혼자서 연다.",
                "대변을 본 뒤 혼자서 뒤처리를 할 수 있다."
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
