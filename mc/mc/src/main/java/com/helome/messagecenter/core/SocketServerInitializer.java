/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.helome.messagecenter.core;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 */
public class SocketServerInitializer extends ChannelInitializer<SocketChannel> {
	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast("decoder", new SocketDecoder());
		pipeline.addLast("encoder", new SocketEncoder());
		/**
		 * IdleStateHandler 构造参数意义
		 * first Parameter ：指定的时间内未进行读IdleState.READER_IDLE将被触发 ，指定0禁用
		 * second parameter ：指定的时间内没有进行写IdleState.WRITER_IDLE将被触发，指定0禁用
		 * third parameter  ：指定的时间内进行读取和写入都IdleState.ALL_IDLE将被触发，指定0禁用
		 * last  parameter  ： 时间单位
		 */
		pipeline.addLast("idleStateHandler", new IdleStateHandler(60, 10, 0, TimeUnit.SECONDS));// 心跳包
        pipeline.addLast("HeartbeatHandler", new HeartbeatHandler());// 心跳包

		pipeline.addLast("handler", new SocketServerHandler());
	}
}
