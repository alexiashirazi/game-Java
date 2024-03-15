package utilz;

public class Constants {

    public static class Directions{
        public static final int LEFT=0;
        public static final int UP=1;
        public static final int RIGHT=2;
        public static final int DOWN=3;

    }
    public static class PlayerConstant{
        public static final int IDLE=0;
        public static final int RUNNING=1;
        public static final int JUMP=2;
        public static final int HURT=3;
        public static final int DEATH=4;
        public static final int ATTACK=5;
        public static final int RUNNING_BACKWARDS=6;



        public static int GetSpriteAmount(int player_action){

            switch(player_action){


                case RUNNING:
                    return 6;
                case JUMP:
                    return 1;
                case ATTACK:
                    return 5;
                case DEATH:
                    return 5;
                case HURT:
                    return 2;
                case RUNNING_BACKWARDS:
                    return 6;
                case IDLE:
                default:
                    return 9;
            }
        }
    }
}
