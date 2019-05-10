package com.example.pactera.gsondemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private  String TAG="MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //反序列化：
        Gson gson=new Gson();

        int i=gson.fromJson("1",Integer.class);
        Log.d(TAG, "=========onCreate: "+i);
        boolean b=gson.fromJson("\"true\"",Boolean.class);
        Log.d(TAG, "=========onCreate: "+b);
        //eg:
        double d = gson.fromJson("1.1",Double.class);
        double d1 = gson.fromJson("\"1.2\"",Double.class);
        String str = gson.fromJson("TomCruise",String.class);
        List<String> stringList = gson.fromJson("[\"aa\",\"bb\"]",new TypeToken<List<String>>(){}.getType());

        //序列化
        String jsonInt = gson.toJson(2);
        Log.d(TAG, "=========onCreate: "+jsonInt);
        String jsonBoolean = gson.toJson(false);    // false
        String jsonString = gson.toJson("String"); //"String"
        Log.d(TAG, "=========onCreate: "+jsonString);
        List<String>list = new ArrayList<>();
        list.add("a");
        list.add("b");
        String jsonList = gson.toJson(list);



        Gson gsonDate = new GsonBuilder()
                //格式化输出
                .setPrettyPrinting()
                //序列化null
                .serializeNulls()
                // 设置日期时间格式，另有2个重载方法
                // 在序列化和反序化时均生效
                .setDateFormat("yyyy-MM-dd")
                // 禁此序列化内部类
                .disableInnerClassSerialization()
                //生成不可执行的Json（多了 )]}' 这4个字符）
                .generateNonExecutableJson()
                //禁止转义html标签
                .disableHtmlEscaping()
                .create();

        User user = new User("John",30);
        user.setDate(new Date()); //日期格式化
        Log.i(TAG,"=========onCreate: format:"+gsonDate.toJson(user));

        //JsonReader读取：
        try {
            JsonReader jsonReader = new JsonReader(new StringReader("[{\"name\":\"Cap\",\"age\":70, \"address\":\"xxx@163.com\"}" +
                    ",{\"name\":\"Thor\",\"age\":1000, \"address\":\"xxx@q.com\"}]"));
            jsonReader.beginArray();
            while (jsonReader.hasNext()){
                jsonReader.beginObject();
                Log.d("JsonReaderArray",jsonReader.nextName()+":"+jsonReader.nextString());
                Log.d("JsonReaderArray",jsonReader.nextName()+":"+jsonReader.nextInt());
                Log.d("JsonReaderArray",jsonReader.nextName()+":"+jsonReader.nextString());
                jsonReader.endObject();
            }

            jsonReader.endArray();
        }catch (Exception e){
            e.printStackTrace();
        }


        //JsonWriter读取：
        try {
            StringWriter writer = new StringWriter();
            JsonWriter jsonWriter = new JsonWriter(writer);
            jsonWriter.beginArray();
            jsonWriter.beginObject();
            jsonWriter.name("name")
                    .value("Stark")
                    .name("age")
                    .value(50)
                    .name("email")
                    .value("xxx@qq.com");
            jsonWriter.endObject();

            jsonWriter.beginObject();
            jsonWriter.name("name")
                    .value("BlackWidow")
                    .name("age")
                    .value(40)
                    .name("email")
                    .value("xxx@qq.com");
            jsonWriter.endObject();
            jsonWriter.endArray();
            jsonWriter.flush();
            Log.i("JsonWriter","writer:"+writer.toString());
        }catch (Exception e){
            e.printStackTrace();
        }


      /*  有两个属性值deserialize默认为true,serialize默认为true。定义在字段上，表明是否在序列化或反序列化过程中暴露出来。
        注： 是不导出的不加
        @Expose 注解直译过来的意思就是暴露，对应所处的暴露的极端
        @Expose
        @Expose(deserialize = true,serialize = true) //序列化和反序列化都都生效，等价于上一条
        @Expose(deserialize = true,serialize = false) //反序列化时生效
        @Expose(deserialize = false,serialize = true) //序列化时生效
        @Expose(deserialize = false,serialize = false) // 和不写注解一样*/

        Gson gsonexpose = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()//Expose生效
//      .serializeNulls()
//      .setPrettyPrinting()
                .create();

        User userexpose = new User("Tom",50);
        User child = new User("Kid",6);
        User parent = new User("parent",80);
        List<User> children = new ArrayList<>();
        children.add(child);
        userexpose.setChildren(children);
        userexpose.setParent(parent);
        Log.d("expose","======User:"+gsonexpose.toJson(userexpose));
        Log.i("expose","=======User:"+gsonexpose.fromJson("{\"age\":50,\"children\":[{\"age\":6,\"name\":\"Kid\"}],\"name\":\"Tom\",\"parent\":{\"age\":80,\"name\":\"parent\"}}"
                ,User.class));
        //属性重命名 @SerializedName 注解的使用
//        {"name":"紫霞","age":1000,"emailAddress":"zixia@example.com"}{"name":"至尊宝","age":2000,"email_address":"zhizunbao@example.com"}{"name":"牛魔王","age":10000,"email":"cow@example.com"}
//        address":"zhizunbao@example.com"}{"name":"牛魔王","age":10000,"email":"cow@example.com"}
//        当上面的三个属性(email_address、email、emailAddress)都中出现任意一个时均可以得到正确的结果。
//
//        @SerializedName(value = "emailAddress", alternate = {"email", "email_address"})public String emailAddress;




//        （三）Gson中使用泛型
//        Gson gson = new Gson();
// String jsonArray = "[\"紫霞\",\"至尊宝\",\"牛魔王\"]";
// String[] strings = gson.fromJson(jsonArray, String[].class);
// List<String> stringList = gson.fromJson(jsonArray, new TypeToken<List<String>>() {}.getType());

//           1.引入泛型可以减少无关的代码

//        {"code":"0","message":"success","data":{}}{"code":"0","message":"success","data":[]}

        //常规定义:
//        public class UserResponse {    public int code;    public String message;    public User data;}

//        通过泛型的话我们可以将code和message字段抽取到一个Result的类中，这样我们只需要编写data字段，如：


//        public class Result<T> {    public int code;    public String message;    public T data;}

//        没有引入泛型之前时写法 ：

//        public class UserResult {    public int code;    public String message;    public User data;}
        //public class UserListResult {    public int code;    public String message;    public List<User> data;}
        //String json = "{..........}";
        // Gson gson = new Gson();
        // UserResult userResult = gson.fromJson(json,UserResult.class);
        // User user = userResult.data;
        // UserListResult userListResult = gson.fromJson(json,UserListResult.class);
        // List<User> users = userListResult.data;

//        /不再重复定义Result类
//        Type userType = new TypeToken<Result<User>>(){}.getType();
//        Result<User> userResult = gson.fromJson(json,userType);
//        User user = userResult.data;
//        Type userListType = new TypeToken<Result<List<User>>>(){}.getType();
//        Result<List<User>> userListResult = gson.fromJson(json,userListType);
//        List<User> users = userListResult.data;




    }
}
