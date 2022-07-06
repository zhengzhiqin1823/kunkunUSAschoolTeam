package com.test.pojo;

public class sc {

        private String cid;
        private String cname;

        public String getCid() {
            return cid;
        }

        public String getCname() {
            return cname;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

    @Override
    public String toString() {
        return "sc{" +
                "cid=" + cid +
                ", cname='" + cname + '\'' +
                '}';
    }
}
