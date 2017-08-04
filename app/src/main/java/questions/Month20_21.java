package questions;
import java.util.ArrayList;

public class Month20_21 extends MonthQuestions {

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
    public Month20_21(String file){

        filename = file;

        answerList = new ArrayList<int[]>();
        answerList.add(new int[]{12,17,23});  // big-muscle
        answerList.add(new int[]{13,18,22});  // small-muscle
        answerList.add(new int[]{10,15,22});  // recognition
        answerList.add(new int[]{5,10,22});   // language
        answerList.add(new int[]{10,15,22});  // social skill
        answerList.add(new int[]{11,16,22});  // self

        teacherImportantPosition = new ArrayList<>();
        // social problem
        teacherImportantPosition.add(32); teacherImportantPosition.add(33); teacherImportantPosition.add(34); teacherImportantPosition.add(35); teacherImportantPosition.add(36); teacherImportantPosition.add(37); teacherImportantPosition.add(38); teacherImportantPosition.add(39);


        questionList = new ArrayList<String[]>();

        questionList.add(new String[]{"뒤뚱거리며 달린다(이미 뒤뚱거리지 않고 자연스럽게 달린다면 '잘 할 수 있다'에 표시한다).",
                "뒷걸음질 친다.",
                "난간을 붙잡고 한 계단에 양발을 모으고 한발 씩 올라간다.",
                "정지되어 있는 공을 발로 찬다.",
                "쪼그리고 앉은 자세에서 아무것도 붙잡지 않고 혼자서 일어선다.",
                "난간을 붙잡고 한 계단에 양발을 모으고 한발씩 계단을 내려간다.",
                "제자리에서 양발을 모아 깡충 뛴다.",
                "계단의 가장 낮은 층에서 두발을 모아 바닥으로 뛰어내린다."});

        questionList.add(new String[]{"연필과 종이를 주면 선을 이리 저리 그리며 낙서를 한다.",
                "블록을 2개 쌓는다",
                "숟가락을 바르게 들어(음식물이 쏟아지지 않도록) 입에 가져간다.",
                "연필의 중간 부분을 잡는다(이미 연필의 아랫부분을 잡으면 '잘 할 수 있다'에 표시한다).",
                "블록을 4개 쌓는다.",
                "블록 2개 이상을 옆으로 나란히 배열한다.",
                "벽면 전등 스위치를 켜고 끈다.",
                "문손잡이를 돌려서 연다."});

        questionList.add(new String[]{"다른 사람의 역할을 흉내 낸다(엄마처럼 인형에게 칭찬하거나 야단을 친다).",
                "동그라미, 네모, 세모와 같은 간단한 도형 맞추기 판에 1 조각을 맞춘다.",
                "두 개의 연속적인 지시를 다른다 (예: 휴지 가지고 와서 물을 닦아).",
                "지시에 따라 신체부위 3개를 가리킨다 (예: 눈, 코, 입, 귀).",
                "그림책에 나온 그림과 같은 실제 사물을 찾는다 (예: 열쇠 그림을 보고 실제 열쇠를 찾는다.)",
                "동물 그림과 동물 소리를 연결한다.",
                "2개의 물건 중에서 큰 것과 작은 것을 구분한다.",
                "빨간, 노란, 파란 토막들으 섞어 놓으면 같은 색의 토막들끼리 분류한다."
        });

        questionList.add(new String[]{"아이에게 익숙한 물건(전화기, 자동차, 책등)을 그림에서 찾으라고 하면 손으로 가리킨다.",
                "'야옹이는 어디 있어요?', '멍멍이는 어디 있어요?'라고 물었을 때, 그림이나 사진을 정확하게 가리킨다.",
                "'엄마', '아빠' 외에 8개 이상의 단어를 말한다.",
                "그림책 속에 등장하는 사물의 이름을 말한다(예: 신발을 가리키며  '이게 뭐지?' 하고 물으면 신발이라고 말한다).",
                "정확하지는 않아도 두 단어로 된 문장을 따라 말한다(예: '까까 주세요', '이게 뭐야?'와 같이 말하면 아이가 따라 말한다).",
                "'나', '이것', '저것' 같은 대명사를 사용한다.",
                "다른 의미를 가진 두 개의 단어를 붙여 말한다(예: '엄마 우유', '장난감 줘', '과자 먹어')",
                "단어의 끝 억양을 높임으로써 질문의 형태로 말한다."
        });

        questionList.add(new String[]{"어른이 시키면 친숙한 어른들에게 인사를 한다.",
                "친숙한 사람의 전화 목소리를 구별한다.",
                "아이가 엄마(보호자)의 관심을 끌기 위해, 주변의 물건들이나 멀리 있는 사물을 손가락으로 가리킨다.",
                "'아기(인형)에게 맘마 주세요'하면 인형에게 먹이는 시늉을 한다.",
                "친숙한 사람이 아프거나 슬퍼하는 것 같으면, 다가와서 위로하려는 듯한 행동이나 말을 한다(예: '호'하고 불어주기, '울지마'라고 말하기).",
                "사람들 앞에서 노래나 율동을 한다.",
                "하고 있는 것을 못하게 하면 '싫어'라고 말한다.",
                "어른이 시키면 '미안해', '고마워'라는 말을 한다."
        });

        questionList.add(new String[]{"어른이 이를 닦는 것을 보고 흉내 내어 이를 닦는다.",
                "어른을 따라서 손을 물에 담갔다가 얼굴을 문질러 세수하는 흉내를 낸다.",
                "음식을 손으로 집어 먹지 않고 포크나 숟가락을 사용하여 먹는다.",
                "혼자서 모자를 쓰고 벗는다.",
                "신발 끈을 풀거나 느슨하게 해주면 혼자서 신발을 벗는다.",
                "손을 씻겨주고 나서 수건을 주면 혼자서 손의 물기를 닦는다.",
                "한손으로 컵을 들고 마신다.",
                "먹을 수 있는 것과 없는 것을 구별 한다(종이, 흙 등은 먹지 않는다.)"
        });

        questionList.add(new String[]{"서거나 걷을 때 항상 까치발을 한다.",
                "걷지 못한다."
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
