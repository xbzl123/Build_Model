package com.example.root.build_model.bean;

import java.util.List;

public class resultBean {
    public result result;
    public String code;

    static public class result{
        public List<songs> songs;
        public String songCount;

        public class songs {
//            public List<songBean> songList;
//            public class songBean{
            public String id;
            public String name;
            public artists[] artists;
            public album album;
            public String audio;
            public int djProgramId;
            public String picUrl;

            public class artists {
                public String id;
                public String name;
                public String picUrl;

            }

            public class album {
                public String id;
                public String name;
                public String picUrl;
                public artists artists;
            }

//        }
        }
    }
}
