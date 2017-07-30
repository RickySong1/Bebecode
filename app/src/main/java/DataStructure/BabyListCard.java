package DataStructure;

/**
 * Created by SuperSong on 2017-03-28.
 */

    public class BabyListCard{

      private int question_id;
        private String question_number;
        private  String question;
        private  int question_type;
        private  int teacher_request;
        private int s_request;
        private  int clicked_radio;
        private int spouse_cliccked;
        private int teacher_clicked;
        private String childID;
        private boolean spouse_request;
        private boolean conflict;
        private boolean hide;
        private int picture_source;
        private String source_name;
        private Boolean comment_on;

        // question_type : big or small or recognition....
        public BabyListCard(int q_id, String question_number, String question, int question_type, int teacher_request, int s_request, int clicked_radio, int spouse_cliccked, int teacher_clicked , String childId, boolean spouse_request, boolean conflict , boolean _hide, int p, String sname, int _comment_on) {
            this.question_number = question_number;
            this.question = question;
            this.question_type = question_type;
            this.teacher_request = teacher_request;
            this.s_request = s_request;
            this.clicked_radio = clicked_radio;
            this.spouse_cliccked = spouse_cliccked;
            this.teacher_clicked = teacher_clicked;
            this.childID = childId;
            this.spouse_request = spouse_request;
            this.conflict = conflict;
            this.hide = _hide;
            this.picture_source = p;
            this.question_id = q_id;
            this.source_name = sname;
            this.comment_on =  (_comment_on == 1);
        }

        public void setNewCard(BabyListCard newone){
            this.question_id = newone.getQuestion_id();
            this.question_number = newone.getQuestion_number();
            this.question = newone.getQuestion();
            this.question_type = newone.getQuestion_type();
            this.teacher_request = newone.getTeacher_request();
            this.s_request = newone.getS_request();
            this.clicked_radio = newone.getClicked_radio();
            this.spouse_cliccked = newone.getSpouse_cliccked();
            this.teacher_clicked = newone.getTeacher_clicked();
            this.childID = newone.getChildID();
            this.spouse_request = newone.isSpouse_request();
            this.conflict = newone.isConflict();
            this.hide = newone.isHide();
            this.picture_source = newone.getPicture_source();
            this.source_name = newone.getSourceName();
        }


        public void setSource_name(String sname){source_name  = sname;}
        private void setQuestion_id(int q_id) { question_id = q_id;}
        public void setPicture_source(int p) { picture_source = p;}
        public void setHide(boolean value){ hide = value;}

    public boolean isHide() { return hide;}

    public boolean isSpouse_request() {
        return spouse_request;
    }

    public boolean isConflict() {
        return conflict;
    }

    public int getPicture_source() { return picture_source; }
    public String getChildID() {
        return childID;
    }

    public void setQuestion_number(String question_number) {
        this.question_number = question_number;
    }

    public void setConflict(Boolean s){ this.conflict = s ;}

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setQuestion_type(int question_type) {
        this.question_type = question_type;
    }

    public void setTeacher_request(int teacher_request) {
        this.teacher_request = teacher_request;
    }

    public void setS_request(int s_request) {
        this.s_request = s_request;
    }

    public void setClicked_radio(int clicked_radio) {
        this.clicked_radio = clicked_radio;
    }

    public void setSpouse_cliccked(int spouse_cliccked) {
        this.spouse_cliccked = spouse_cliccked;
    }

    public void setTeacher_clicked(int teacher_clicked) {
        this.teacher_clicked = teacher_clicked;
    }

    public String getSourceName() { return source_name; }
    public int getQuestion_id() { return question_id; }
    public int getSpouse_cliccked() { return spouse_cliccked; }
        public int getTeacher_clicked() { return teacher_clicked; }
        public String getQuestion_number() {
                return question_number;
            }
        public int getS_request() { return s_request; }
        public String getQuestion() { return question; }
        public int getQuestion_type() {
                return question_type;
            }
        public int getTeacher_request() {return teacher_request;}
        public int getClicked_radio() {
            return clicked_radio;
        }
        public Boolean isCommentOn() { return comment_on; }

        public String printS(){
            return question_number +","+Integer.toString(question_type)+","+Integer.toString(teacher_request)+","+Integer.toString(s_request)+","+Integer.toString(clicked_radio)+","+Integer.toString(spouse_cliccked)+","+Integer.toString(teacher_clicked);
        }
    }

