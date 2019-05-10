package com.wrlus.seciot.agent.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wrlus.seciot.agent.service.AgentAndroidServiceImpl;
import com.wrlus.seciot.util.exception.ReasonEnum;
import com.wrlus.seciot.util.exception.RootException;

@Controller
@RequestMapping("/agent")
public class AgentController {
	private static Logger log = LogManager.getLogger();
	@Autowired
	private AgentAndroidServiceImpl agentAndroidService;
	
	@ResponseBody
	@RequestMapping("/frida-version")
	public Map<String, Object> getFridaVersion(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> data = new HashMap<>();
		try {
			String version = agentAndroidService.getFridaVersion();
			data.put("status", 0);
			data.put("reason", ReasonEnum.SUCCESS.get());
			data.put("version", version);
		} catch (RootException e) {
			log.error(e.getClass().getName() + ": " + e.getLocalizedMessage());
			if (log.isDebugEnabled()) {
				e.printStackTrace();
			}
			data.put("status", -1);
			data.put("reason", e.getReason().get());
		} catch (Exception e) {
			log.error(e.getClass().getName() + ": " + e.getLocalizedMessage());
			if (log.isDebugEnabled()) {
				e.printStackTrace();
			}
			data.put("status", -1);
			data.put("reason", ReasonEnum.UNKNOWN.get());
		}
		return data;
	}
}