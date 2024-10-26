# 漫游城——citywalker社群平台先行者

> 仅作简单介绍，详细 Json 返回数据等见 swagger

---

---

## 统一类型

---

返回 `Result` 类型的数据，`Result` 类型定义如下：

``````java
public class Result {
    private int code;
    private String message;
    private Object result;
}
``````

---

## 接口需求

---

### 图片

#### 上传图片

- 请求参数：`file: MultipartFile`
- 返回数据：`Result<string>`，`string` 为图片的 URL

---

### 登录

#### 用户登录

- 请求参数：`code: String`
- 返回数据：`Result<LoginInfo>` (用户存在时返回，不存在时直接创建用户)
- `LoginInfo` 类型定义如下：

``````java
public class LoginInfo {
    private String userId;
    private Integer role;
    private String sessionId;
    private SessionData sessionData;
}
``````

---

### 用户

- 用户类（User）大致如下：

``````java
public class User implements Serializable {
    private String id;
    // 0是超级管理员，1是管理员，2是已认证用户，3是同意协议用户，4是未同意协议用户
    private Integer role;
	// 用户名
    private String username;
	// 个性签名
    private String signature;
	// 加入购物车中的路线
    private String cartRoutes;
	// 生日
    private Date birthday;
	// 邮箱
    private String email;
	// 头像
    private String headportrait;
	// 性别 0为女性 1为男性 2为私密
    private Integer gender;
	// 年龄
    private Integer age;
	// 个人标签
    // 返回给前端时以 List<String> 形式
    private String label;
    private int version;
}
``````

- 用户返回类（ShowUserResponse）大致如下：

``````java
public class ShowUserResponse {
	// 用户id
    private String id;
	// 0是超级管理员，1是管理员，2是已认证用户，3是同意协议用户，4是未同意协议用户
    private Integer role;
	// 用户名
    private String username;
	// 个性签名
    private String signature;
	// 生日
    private Date birthday;
	// 邮箱
    private String email;
	// 头像
    private String headportrait;
	// 性别 0为女性 1为男性 2为私密
    private Integer gender;
	// 年龄
    private Integer age;
	// 个人标签
    private List<String> label;
    private int version;
}
``````

#### getUserById

- 权限：`<= 3`
- 请求参数：`userId: String`
- 返回数据：`Result<User>`

#### getUserByName

- 权限：`<= 1`
- 请求参数：`name: string`
- 返回数据：`Result<User[]>`
- 注意：模糊搜索，包含姓名就算匹配

#### updateUser

- 权限：`<= 3`
- 请求参数：`[FromBody] user: UpdateUserParams`
- 返回数据：`Result<User>`

```ts
class updateUserParams {
  name?: string
  email?: string
  avatar?: string
  status?: string
  gender?: 0 | 1 | 2 | 3
  grade?: number
  major?: string
}
```

#### updateUserRole

- 权限：`<= 0`
- 请求参数：`userId: string, role: 0 | 1 | 2 | 3`
- 返回数据：`Result<User>`

#### banUser

- 权限：`<= 1`
- 请求参数：`userId: string, bannedBefore: Date`
- 返回数据：`Result<User>`

#### vertifyUser

- 权限：`<= 3`
- 请求参数：`userId: string`
- 返回数据：`Result<User>`
- 描述：将用户设为已认证用户

