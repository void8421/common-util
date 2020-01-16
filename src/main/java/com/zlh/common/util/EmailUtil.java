package com.zlh.common.util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 * 依赖jar包，commons-email-.*.*.*.jar,commons-io-*.*.jar
 *
 * <pre>
 * 类名称    : EmailUtils
 * 类描述    : 发送Emial(发送纯文本的邮件，带附件的邮件，html的邮件)
 * </pre>
 * @author 10448
 */
public class EmailUtil {

	/**
	 * 设置邮件服务器
	 */
	private static final String SEND_EMAIL_HOST_NAME = PropsUtils.getDefaultKeyFromClasspath("send_email_host_name");

	/**
	 * 设置是否使用安全连接
	 */
	private static final String SEND_EMAIL_USE_SSL = PropsUtils.getDefaultKeyFromClasspath("send_email_use_ssl");

	/**
	 * 设置安全连接端口号
	 */
	private static final String SEND_EMAIL_SSL_PORT = PropsUtils.getDefaultKeyFromClasspath("send_email_ssl_port");

	/**
	 * 发送Email用户名
	 */
	private static final String SEND_EMAIL_USER_NAME = PropsUtils.getDefaultKeyFromClasspath("send_email_user_name");

	/**
	 * 发送Email密码
	 */
	private static final String SEND_EMAIL_USER_PWD = PropsUtils.getDefaultKeyFromClasspath("send_email_user_pwd");

	/**
	 * 发送Email编码
	 */
	private static final String SEND_EMAIL_CHARSET = "UTF-8";

    /**
     * 发送纯文本的Email
     *
     * @param receiver 收信人
     * @param subject  Email主题
     * @param msg      Email内容
     * @throws EmailException
     */
    public static void sendPlainEmail(String receiver, String subject, String msg) throws EmailException {
        SimpleEmail email = new SimpleEmail();
        email.setHostName(SEND_EMAIL_HOST_NAME);
        boolean is_ssl = false;
        if (!ValidateUtil.validateBlank(SEND_EMAIL_USE_SSL) && SEND_EMAIL_USE_SSL.equals("true")) {
            is_ssl = true;
        }
        email.setSSL(is_ssl);
        email.setSslSmtpPort(SEND_EMAIL_SSL_PORT);
        email.setAuthentication(SEND_EMAIL_USER_NAME, SEND_EMAIL_USER_PWD);
        if (!ValidateUtil.validateBlank(receiver)) {
            try {
                email.addTo(receiver);
                email.setCharset(SEND_EMAIL_CHARSET);
                email.setFrom(SEND_EMAIL_USER_NAME);
                email.setSubject(subject);
                email.setMsg(msg);
                email.send();
            } catch (EmailException e) {
                throw new EmailException(e);
            }
        }
    }

	public static void main(String[] args) throws EmailException {
		EmailUtil.sendPlainEmail("1044829344@qq.com", "MES智能助手", "send from idea");
	}

//	/**
//	 * 发送多个带附件的Email
//	 * 
//	 * @param receiver  收信人
//	 * @param multipart 附件map key:附件名字不需加文件扩展名,map value:附件路径和名字，如果为网络资源，请以http开头
//	 * @param subjecct  Email主题
//	 * @param msg       Email内容
//	 * @throws EmailException
//	 */
//	public static void sendMultipartEmail(String receiver, Map<String, String> multipart, String subjecct, String msg)
//			throws EmailException {
//		MultiPartEmail email = new MultiPartEmail();
//		email.setHostName(send_email_host_name);
//		boolean is_ssl = false;
//		if (!ValidateUtil.validateBlank(send_email_use_ssl) && send_email_use_ssl.equals("true"))
//			is_ssl = true;
//		email.setSSL(is_ssl);
//		email.setSslSmtpPort(send_email_ssl_port);
//		email.setAuthentication(send_email_user_name, send_email_user_pwd);
//		Iterator<Entry<String, String>> iter = multipart.entrySet().iterator();
//		String encoding = System.getProperty("file.encoding");
//		BASE64Encoder encoder = new BASE64Encoder();
//		while (iter.hasNext()) {
//			Map.Entry<java.lang.String, java.lang.String> entry = (Map.Entry<java.lang.String, java.lang.String>) iter
//					.next();
//			String multiPath = entry.getValue();
//			String multiName = entry.getKey() + "." + FilenameUtils.getExtension(multiPath);
//			if (!ValidateUtil.validateBlank(multiName) && !ValidateUtil.validateBlank(multiPath)) {
//				EmailAttachment attachment = new EmailAttachment();
//				if (multiPath.startsWith("http:")) {
//					try {
//						attachment.setURL(new URL(multiPath));
//					} catch (MalformedURLException e) {
//						throw new EmailException(e);
//					}
//				} else {
//					attachment.setPath(multiPath);
//				}
//				attachment.setName("=?" + encoding + "?B?" + encoder.encode(multiName.getBytes()) + "?=");
//				attachment.setDisposition(EmailAttachment.ATTACHMENT);
//				attachment.setDescription("");
//				email.attach(attachment);
//			}
//		}
//		if (!ValidateUtil.validateBlank(receiver)) {
//			try {
//				email.addTo(receiver, "receiver");
//				email.setCharset(send_email_charset);
//				email.setFrom(send_email_user_name);
//				email.setSubject(subjecct);
//				email.setMsg(msg);
//				email.send();
//			} catch (EmailException e) {
//				throw new EmailException(e);
//			}
//		}
//	}
//
//	/**
//	 * 发送Html的Email
//	 * 
//	 * @param receiver 收信人
//	 * @param subjecct Email主题
//	 * @param msg      Email内容
//	 * @throws EmailException
//	 */
//	public static void sendHtmlEmail(String receiver, String subjecct, String msg, String sendName)
//			throws EmailException {
//		System.out.print("系统正在发送邮箱，接受者:" + receiver + "，标题：" + subjecct + "，内容：" + msg);
//		HtmlEmail email = new HtmlEmail();
//		email.setHostName(send_email_host_name);
//		boolean is_ssl = false;
//		if (!ValidateUtil.validateBlank(send_email_use_ssl) && send_email_use_ssl.equals("true"))
//			is_ssl = true;
//		email.setSSL(is_ssl);
//		email.setSslSmtpPort(send_email_ssl_port);
//		email.setAuthentication(send_email_user_name, send_email_user_pwd);
//		if (!ValidateUtil.validateBlank(receiver)) {
//			try {
//				email.addTo(receiver, "receiver");
//				email.setCharset(send_email_charset);
//				email.setFrom(send_email_user_name, sendName);
//				email.setSubject(subjecct);
//				email.setHtmlMsg(msg);
//				email.send();
//			} catch (EmailException e) {
//				throw new EmailException(e);
//			}
//		}
//	}
}