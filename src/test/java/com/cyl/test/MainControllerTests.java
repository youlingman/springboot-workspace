package com.cyl.test;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyl.test.controller.MainController;
import com.cyl.test.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MainController.class)
public class MainControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testPostJson() throws Exception {
	    User user = new User();
	    user.setId("1");
	    user.setName("John");
        JSONObject object = new JSONObject();
        object.put("user", user);
        this.mockMvc.perform(get("/json")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("500")));
        this.mockMvc.perform(post("/json").contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(user))).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("John")));
	}

}
