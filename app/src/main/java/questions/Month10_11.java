package questions;
import java.util.ArrayList;

public class Month10_11 extends MonthQuestions {

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

    public boolean isShortMonth(){
        return true;
    }

    private String filename;

    public String getFileName() {
        return filename;
    }

    public Month10_11(String file){
        filename = file;

        answerList = new ArrayList<int[]>();
        answerList.add(new int[]{15,18,23});  // big-muscle
        answerList.add(new int[]{15,19,23});  // small-muscle
        answerList.add(new int[]{15,19,23});  // recognition
        answerList.add(new int[]{11,17,23});   // language
        answerList.add(new int[]{13,17,23});  // social skill
        answerList.add(new int[]{0,0,0});  // self

        teacherImportantPosition = new ArrayList<>();
        // social problem
        teacherImportantPosition.add(32); teacherImportantPosition.add(33); teacherImportantPosition.add(34); teacherImportantPosition.add(35); teacherImportantPosition.add(36); teacherImportantPosition.add(37); teacherImportantPosition.add(38); teacherImportantPosition.add(39);


        questionList = new ArrayList<String[]>();

        questionList.add(new String[]{"누워 있다가 혼자 앉는다.",
                "양손과 무릎으로 긴다 (네발기기).",
                "가구를 붙잡고 일어선다.",
                "가구를 붙잡은 상태에서 넘어지지 않고 자세를 낮춘다.",
                "가구를 양손으로 붙잡고 옆으로 걷는다.",
                "5초 이상 혼자 서 있는다.",
                "한 손으로 가구를 붙잡고 걷는다.",
                "아무것도 붙잡지 않고 혼자 일어선다."});

        questionList.add(new String[]{"딸랑이를 쥐고 있는 손에 다른 장난감을 주면 쥐고 있던 딸랑이를 떨어뜨리고 새 장난감을 잡는다.",
                "두 개의 물건을 양 손에 각각 따로 쥔다.",
                "엄지와 다른 손가락을 이용해 작은 과자를 집는다.",
                "장난감을 한 손에서 다른 손으로 옮겨 쥔다.",
                "손잡이를 사용하여 컵을 잡는다.",
                "우유병을 혼자서 잡고 먹는다.",
                "엄지손가락과 집게손가락 끝을 사용하여 집게 모양으로 작은 알약 크기의 과자를 집는다.",
                "장난감 자동차를 잡고 바퀴가 앞으로 굴러가도록 민다."});

        questionList.add(new String[]{"어른이 안으려고 하면 팔을 벌린다.",
                "그림책에 재미있는 그림이 있으면 관심있게 쳐다본다.",
                "리듬에 맞추어 몸을 움직인다.",
                "상자 안에서 물건을 꺼낸다.",
                "어른이 아이가 내는 소리를 따라하면, 아이가 다시 그 소리를 따라한다.",
                "장난감에 있는 버튼을 눌러 소리가 나게 한다.",
                "자신이 좋아하는 한 개의 장난감을 가지고 3-4분 정도 논다.",
                "아이가 보는 앞에서 작은 장난감을 컵으로 덮으면 컵을 열어 장난감을 찾는다."
        });

        questionList.add(new String[]{"아이에게 '안돼요' 라고 하면, 짧은 순간이라도 하던 행동을 멈추고 목소리에 반응한다.",
                "'무무', '바바바', '다다', '마마마'등의 소리를 반복해서 발성한다.",
                "동작을 보여주지 않고 말로만 '빠이빠이', '짝짜꿍', '까꿍'을 시키면 최소한 한가지를 한다.",
                "엄마에게 엄마라고 말한다.",
                "'다', '가', '카', '바'등과 같이 자음과 모음이 합쳐진 소리를 낸다.",
                "동작을 보여주지 않고 말로만 '주세요', '오세요', '가자', '밥먹자'를 말하면, 2가지 이상의 뜻을 이해한다.",
                "원하는 것을 손가락으로 가리킨다.",
                "'좋다(예)', '싫다(아니오)'를 몸이나 고개를 흔들어 표현한다"
        });

        questionList.add(new String[]{"낯가림을 한다.",
                "친숙한 어른에게 안아달라고 팔을 벌린다.",
                "어른을 다라서 손뼉을 치며 짝짜꿍 놀이를 한다.",
                "다른 아이들 옆에서 논다 (함께 놀이를 하지는 못해도 된다.)",
                "어른을 따라서 까꿍 놀이를 한다.",
                "어른을 따라서 '빠이빠이' 하면서 손을 흔든다.",
                "어른의 관심을 끌기위한 행동을 한다 (예: 어른이 못본 척 하면 '예쁜 짓'을 한다.)",
                "'곤지곤지'를 듣고 양 손을 움직거린다."
        });

        questionList.add(new String[]{"없음",
                "없음",
                "없음",
                "없음",
                "없음",
                "없음",
                "없음",
                "없음"
        });

        questionList.add(new String[]{"한쪽 손만 사용한다.",
                "서거나 걸을 때 항상 까치발을 한다."
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
