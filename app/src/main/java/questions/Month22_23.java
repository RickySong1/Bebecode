package questions;
import java.util.ArrayList;

public class Month22_23 extends MonthQuestions {

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

    public Month22_23(){

        answerList = new ArrayList<int[]>();
        answerList.add(new int[]{7,18,23});  // big-muscle
        answerList.add(new int[]{8,17,22});  // small-muscle
        answerList.add(new int[]{8,15,22});  // recognition
        answerList.add(new int[]{4,13,23});   // language
        answerList.add(new int[]{5,15,22});  // social skill
        answerList.add(new int[]{4,15,22});  // self

        teacherImportantPosition = new ArrayList<>();
        // social problem
        teacherImportantPosition.add(32); teacherImportantPosition.add(33); teacherImportantPosition.add(34); teacherImportantPosition.add(35); teacherImportantPosition.add(36); teacherImportantPosition.add(37); teacherImportantPosition.add(38); teacherImportantPosition.add(39);


        questionList = new ArrayList<String[]>();

        // big muscle
        questionList.add(new String[]{"뒷걸음질 친다.",
                "정지되어 있는 공을 발로 찬다.",
                "난간을 붙잡고 한 계단에 양발을 모으고 한발씩 계단을 내려간다.",
                "제자리에서 양발을 모아 깡충 뛴다.",
                "계단의 가장 낮은 층에서 두발을 모아 바닥으로 뛰어내린다.",
                "서있는 자세에서 머리 위로 팔을 높이 들어 공을 앞으로 던진다.",
                "난간을 붙잡고 한발씩 번갈아 내딛으며 계단을 올라간다.",
                "발뒤꿈치를 들고 발끝으로 네 걸음 이상 걷는다."});

        // small muscle
        questionList.add(new String[]{"숟가락을 바르게 들어(음식물이 쏟아지지 않도록) 입에 가져간다.",
                "연필의 중간 부분을 잡는다(이미 연필의 아랫부분을 잡으면 '잘 할 수 있다'에 표시한다.",
                "블록을 4개 쌓는다.",
                "블록 2개 이상을 옆으로 나란히 배열한다.",
                "벽면 전등 스위치를 켜고 끈다.",
                "문손잡이를 돌려서 연다.",
                "연필의 아랫부분을 잡는다.",
                "유아용 가위를 주면 실제로 종이를 자르지는 못해도 한 손으로 종이를 잡고 다른 손으로는 가위 날을 벌리고 오므리며 종이를 자르려고 시도한다."});

        // recognition
        questionList.add(new String[]{"두 개의 연속적인 지시를 따른다(예: 휴지 가지고 와서 물을 닦아)",
                "지시에 따라 신체부위 3개를 가리킨다(예: 눈, 코, 입, 귀)",
                "그림책에 나온 그림과 같은 실제 사물을 찾는다(예: 열쇠 그림을 보고 실제 열쇠를 찾는다)",
                "동물 그림과 동물 소리를 연결한다.",
                "2개의 물건 중에서 큰 것과 작은 것을 구분한다.",
                "빨간, 노란, 파란 토막들을 섞어 놓으면 같은 색의 토막들끼리 분류한다.",
                "동그라미, 네모, 세모와 같이 간단한 도형 맞추기 판에 3조각 이상 맞춘다.",
                "'많다-적다'와 같은 양의 개념을 이해한다(예: 사탕 2개와 사탕6개를 놓고 어떤 것이 더 많은지 물었을 때 많은 것을 가리킬 수 있다)"
        });

        //language
        questionList.add(new String[]{"'엄마', '아빠' 외에 8개 이상의 단어를 말한다.",
                "그림책 속에 등장하는 사물의 이름을 말한다(예: 신발을 가리키며 '이게 뭐지?' 하고 물으면 신발이라고 말한다)",
                "정확하지는 않아도 두 단어로 된 문장을 따라 말한다(예: '까까 주세요', '이게 뭐야?'와 같이 말하면 아이가 따라 말한다)",
                "'나', '이것', '저것' 같은 대명사를 사용한다.",
                "다른 의미를 가진 두 개의 단어를 붙여 말한다(예: '엄마 우유', '장난감 줘', '과자 먹어')",
                "단어의 끝 억양을 높임으로써 질문의 형태로 말한다.",
                "자기 물건에 대해 '내 것'이란 표현을 한다.",
                "손으로 가리키거나 동작으로 힌트를 주지 않아도, '식탁 위에 컵을 놓으세요'라고 말하면 아이가 정확하게 행동한다."
        });

        // social skills
        questionList.add(new String[]{"아이가 엄마(보호자)의 관심을 끌기 위해, 주변의 물건들이나 멀리 있는 사물을 손가락으로 가리킨다.",
                "'아기(인형)에게 맘마 주세요' 하면 인형에게 먹이는 시늉을 한다.",
                "친숙한 사람이 아프거나 슬퍼하는 것 같으면, 다가와서 위로하려는 듯한 행동이나 말을 한다(예: '호'하고 불어주기, '울지마'라고 말하기).",
                "사람들 앞에서 노래나 율동을 한다.",
                "하고 있는 것을 못하게 하면 '싫어'라고 말한다.",
                "어른이 시키면 '미안해', '고마워'라는 말을 한다.",
                "'나 좀 봐'라고 말하며 자신이 하는 행동을 봐주기를 요청한다.",
                "다른 아이들의 행동을 보고 (간단한) 놀이의 규칙을 따른다."
        });

        // self
        questionList.add(new String[]{"혼자서 모자를 쓰고 벗는다.",
                "신발 끈을 풀거나 느슨하게 해주면 혼자서 신발을 벗는다.",
                "손을 씻겨주고 나서 수건을 주면 혼자서 손의 물기를 닦는다.",
                "한손으로 컵을 들고 마신다.",
                "외투의 단추를 풀어주면 혼자서 벗는다.",
                "먹을 수 있는 것과 없는 것을 구별 한다(종이, 흙 등은 먹지 않는다)",
                "혼자서 슬리퍼를 신는다.",
                "어른이 코를 닦으라고 말해주면, 휴지로 코를 닦는다."
        });

        questionList.add(new String[]{"걷지 못한다."
        });


		 /*

		 for(int i=0; i<questionList.size(); i++){
			 for(int j =0 ; j<questionList.get(i).length ; j++)
			 {
	           System.out.print(questionList.get(i)[j] + "\t");
			 }
			 System.out.println("\n");
		 }
		 */


    }


}
