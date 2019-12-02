package com.cyl.test;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyl.test.controller.MainController;
import com.cyl.test.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
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
	    user.setId(1);
	    user.setName("John");
        this.mockMvc.perform(get("/json"))
				.andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("500")));
		this.mockMvc.perform(get("/json").contentType(MediaType.APPLICATION_JSON).content("id=1&name=John"))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("500")));
		String resContent = this.mockMvc.perform(post("/json").contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(user))).andDo(print()).andReturn().getResponse().getContentAsString();
		Assert.assertEquals(user, ((JSONObject)JSON.parseObject(resContent).get("user")).toJavaObject(User.class));
	}

	@Test
	public void testParams() throws Exception {
		JSONObject withParamObj = new JSONObject();
		withParamObj.put("id", "1");
		withParamObj.put("name", "John");
		withParamObj.put("modified", "params");
		JSONObject noParamObj = new JSONObject();
		noParamObj.put("modified", "params");
		String params = new StringBuilder().append("id=").append(withParamObj.get("id")).append("&name=").append(withParamObj.get("name")).toString();
		String resContent;
		// no contentType
		resContent = this.mockMvc.perform(post("/params").content(params))
				.andDo(print()).andReturn().getResponse().getContentAsString();
		// non match contentType
		Assert.assertEquals(noParamObj, JSON.parseObject(resContent));
		resContent = this.mockMvc.perform(post("/params").contentType(MediaType.APPLICATION_JSON).content(params))
				.andDo(print()).andReturn().getResponse().getContentAsString();
		Assert.assertEquals(noParamObj, JSON.parseObject(resContent));
		resContent = this.mockMvc.perform(post("/params").contentType(MediaType.MULTIPART_FORM_DATA).content(params))
				.andDo(print()).andReturn().getResponse().getContentAsString();
		Assert.assertEquals(noParamObj, JSON.parseObject(resContent));
		// match contentType
		resContent = this.mockMvc.perform(post("/params").contentType(MediaType.APPLICATION_FORM_URLENCODED).content(params))
				.andDo(print()).andReturn().getResponse().getContentAsString();
		Assert.assertEquals(withParamObj, JSON.parseObject(resContent));
	}


	@Test
	public void testParamsObj() throws Exception {
		JSONObject user = new JSONObject();
		user.put("id", 1);
		user.put("name", "John");
		JSONObject car = new JSONObject();
		car.put("engine", "fire");
		car.put("model", "tesla");
		car.put("id", 1);
		JSONObject res = new JSONObject();
		res.put("modified", "params");
		res.put("car", car);
		res.put("user", user);
		String params = new StringBuilder().append("id=").append(user.get("id"))
				.append("&name=").append(user.get("name"))
				.append("&engine=").append(car.get("engine"))
				.append("&model=").append(car.get("model"))
				.toString();
		String resContent;
		// no contentType
		resContent = this.mockMvc.perform(post("/params-obj").contentType(MediaType.APPLICATION_FORM_URLENCODED).content(params))
				.andDo(print()).andReturn().getResponse().getContentAsString();
		Assert.assertEquals(res, JSON.parseObject(resContent));
	}


}
