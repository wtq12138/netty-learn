package com.example.demo.nettychat.server.handler;

import com.example.demo.nettychat.message.LoginRequestMessage;
import com.example.demo.nettychat.message.LoginResponseMessage;
import com.example.demo.nettychat.server.service.UserServiceFactory;
import com.example.demo.nettychat.server.session.Session;
import com.example.demo.nettychat.server.session.SessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @program: demo
 * @description:
 * @author: wtq12138
 * @create: 2022-05-06 16:29
 */
@ChannelHandler.Sharable
public class LoginRequestMessageHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
        boolean login = UserServiceFactory.getUserService().login(msg.getUsername(), msg.getPassword());
        LoginResponseMessage message;
        if (login) {
            Session session= SessionFactory.getSession();
            session.bind(ctx.channel(),msg.getUsername());
            message = new LoginResponseMessage(true, "ok");
        } else {
            message = new LoginResponseMessage(false, "no");
        }
        ctx.writeAndFlush(message);
    }
}
