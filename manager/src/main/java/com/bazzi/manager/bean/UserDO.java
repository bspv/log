package com.bazzi.manager.bean;

import com.bazzi.manager.model.User;
import com.bazzi.manager.vo.response.UserResponseVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDO implements Serializable {
    private static final long serialVersionUID = 4752889496469309571L;

    private User user;
    private UserResponseVO userResponseVO;
}
