import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnCapsulation {
    public static void main(String[] args) throws Exception {
       TestBeans beans = new TestBeans();
       beans.setNum(5);
       beans.setFlag(false);
       System.out.println(beans.getNum()); // 5
       System.out.println(beans.isFlag()); // false

       List<User> users = new ArrayList<User>(Arrays.asList(new User(1, "太郎"), new User(2, "次郎")));
       TestImmutable im = new TestImmutable(10, users);
       System.out.println(im.getNum()); // 10
       System.out.println(im.getUsers()); // [User@5ca881b5, User@24d46ca6] ※@以降はオブジェクトによる
    }
}

// Javabeans
class TestBeans {
    private int num;

    private boolean flag;

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum() {
        return this.num;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return this.flag;
    }
}

class User {
    private int id;
    private String name;

    User(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

// Immutableクラス
final class TestImmutable {
    private final int num;

    private final List<User> users;

    public TestImmutable(int num, List<User> users) {
        this.num = num;
        List<User> userList = new ArrayList<User>();
        userList.addAll(users);
        this.users = userList;
    }

    public int getNum() {
        return this.num;
    }

    public List<User> getUsers() {
        List<User> userList = new ArrayList<User>();
        userList.addAll(users);
        return userList;
    }
}
