package com.key.win.websocket.ctrl;

import com.key.win.basic.exception.BizException;
import com.key.win.basic.util.IndividualSoldierAuthConstantUtils;
import com.key.win.basic.web.Result;
import com.key.win.common.auth.AuthenticationUtil;
import com.key.win.log.annotation.LogAnnotation;
import com.key.win.websocket.manager.WebSocketManager;
import com.key.win.websocket.vo.WebsocketGroupMessage;
import com.key.win.websocket.vo.WebsocketTokenMessage;
import com.key.win.websocket.vo.WebsocketTokensMessage;
import com.key.win.websocket.vo.WebsocketUserMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@Api("webSocket相关的api")
@RequestMapping("/ws")
public class WebSocketCtrl {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private WebSocketManager webSocketManager;

//    @Autowired
//    private SysUserService sysUserService;

    @ApiOperation(value = "给单用户发送信息")
    @GetMapping("/sendByUserName/{userName}/{message}")
    @LogAnnotation(module = "websocket-center", recordRequestParam = false)
    public Result sendByUserName(@PathVariable String userName, @PathVariable String message) {
        WebsocketUserMessage websocketUserMessage = new WebsocketUserMessage();
        websocketUserMessage.setToUserName(userName);
        websocketUserMessage.setMessage(message);
        webSocketManager.sendMessageByUserName(userName, websocketUserMessage);
        return Result.succeed(IndividualSoldierAuthConstantUtils.RESULT_SUCCESS_DEFAULT_MESSAGE);
    }

    @ApiOperation(value = "给单用户发送信息")
    @GetMapping("/sendByToken/{toToken}/{message}")
    @LogAnnotation(module = "websocket-center", recordRequestParam = false)
    public Result sendByToken(@PathVariable String toToken, @PathVariable String message) {
        WebsocketTokenMessage websocketTokenMessage = new WebsocketTokenMessage();
        websocketTokenMessage.setToToken(toToken);
        websocketTokenMessage.setMessage(message);
        webSocketManager.sendMessageByToken(toToken, websocketTokenMessage);
        return Result.succeed(IndividualSoldierAuthConstantUtils.RESULT_SUCCESS_DEFAULT_MESSAGE);
    }

    @ApiOperation(value = "给单用户发送信息")
    @PostMapping("/sendByUserName/msg")
    @LogAnnotation(module = "websocket-center", recordRequestParam = false)
    public Result sendMsgByUserName(@RequestBody WebsocketUserMessage websocketUserMessage) {
        checkProperty(websocketUserMessage.getToUserName(), "接收人为空！");
        checkProperty(websocketUserMessage.getMessage(), "发送消息为空！");
        webSocketManager.sendMessageByUserName(websocketUserMessage.getToUserName(), websocketUserMessage);
        return Result.succeed(IndividualSoldierAuthConstantUtils.RESULT_SUCCESS_DEFAULT_MESSAGE);
    }

    @ApiOperation(value = "给单用户发送信息")
    @PostMapping("/sendByToken/msg")
    @LogAnnotation(module = "websocket-center", recordRequestParam = false)
    public Result sendMsgByToken(@RequestBody WebsocketTokenMessage websocketTokenMessage) {
        checkProperty(websocketTokenMessage.getToToken(), "toToken为空！");
        checkProperty(websocketTokenMessage.getMessage(), "发送消息为空！");
        webSocketManager.sendMessageByToken(websocketTokenMessage.getToToken(), websocketTokenMessage);
        return Result.succeed(IndividualSoldierAuthConstantUtils.RESULT_SUCCESS_DEFAULT_MESSAGE);
    }

    @ApiOperation(value = "给多用户发送信息")
    @PostMapping("/sendByTokens/msg")
    @LogAnnotation(module = "websocket-center", recordRequestParam = false)
    public Result sendMsgByTokens(@RequestBody WebsocketTokensMessage websocketTokensMessage) {
        if (CollectionUtils.isEmpty(websocketTokensMessage.getToTokens())) {
            logger.error("接收人列表为空！");
            throw new IllegalArgumentException("接收人列表为空！");
        }
        checkProperty(websocketTokensMessage.getMessage(), "发送消息为空！");
        websocketTokensMessage.getToTokens().forEach(token -> {
            if (!token.equals(AuthenticationUtil.getToken())) {
                webSocketManager.sendMessageByToken(token, websocketTokensMessage);
            }
        });
        return Result.succeed(IndividualSoldierAuthConstantUtils.RESULT_SUCCESS_DEFAULT_MESSAGE);
    }

    @ApiOperation(value = "给用户列表发送信息")
    @PostMapping("/send/group/msg")
    @LogAnnotation(module = "websocket-center", recordRequestParam = false)
    public Result sendGroupMsg(@RequestBody WebsocketGroupMessage websocketGroupMessage) {
        checkProperty(websocketGroupMessage.getMessage(), "发送消息为空！");
        if (CollectionUtils.isEmpty(websocketGroupMessage.getToUserNames())) {
            logger.error("接收人列表为空！");
            throw new IllegalArgumentException("接收人列表为空！");
        }
        websocketGroupMessage.getToUserNames().forEach(toUserName -> {
            if (!toUserName.equals(AuthenticationUtil.getUserName())) {
                webSocketManager.sendMessageByUserName(toUserName, websocketGroupMessage);
            }
        });
        return Result.succeed(IndividualSoldierAuthConstantUtils.RESULT_SUCCESS_DEFAULT_MESSAGE);
    }

//    @ApiOperation(value = "给用户列表发送组信息")
//    @PostMapping("/send/groupId/msg")
//    @LogAnnotation(module = "websocket-center", recordRequestParam = false)
//    public Result sendGroupIdMsg(@RequestBody WebsocketGroupIdMessage websocketGroupIdMessage) {
//        checkProperty(websocketGroupIdMessage.getMessage(), "发送消息为空！");
//        checkProperty(websocketGroupIdMessage.getGroupId(), "组Id为空！");
//        List<SysUser> sysUsers = sysUserService.findSysUserByGroupId(Long.parseLong(websocketGroupIdMessage.getGroupId()));
//        if (CollectionUtils.isEmpty(sysUsers)) {
//            logger.error("接收人列表为空！");
//            throw new BizException("接收人列表为空！");
//        }
//        sysUsers.forEach(sysUser -> {
//            if (!sysUser.getUserName().equals(AuthenticationUtil.getUserName())) {
//                webSocketManager.sendMessageByUserName(sysUser.getUserName(), websocketGroupIdMessage);
//            }
//        });
//        return Result.succeed(SingleSoldierConstantUtils.RESULT_SUCCESS_DEFAULT_MESSAGE);
//    }


    private void checkProperty(String message, String s) {
        if (StringUtils.isBlank(message)) {
            logger.error(s);
            throw new BizException(s);
        }
    }
}
