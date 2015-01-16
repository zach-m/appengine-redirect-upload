package com.tectonica;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

@SuppressWarnings("serial")
public class UploadServlet extends HttpServlet
{
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	private static Logger LOG = Logger.getLogger(UploadServlet.class.getName());

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		String base = baseUrlOf(req);
		LOG.info("BaseURL = " + base);

		String uploadUrl = blobstoreService.createUploadUrl("/postUpload");
		LOG.info("UploadURL = " + uploadUrl);

		String dispatchUrl = uploadUrl.substring(base.length());
		LOG.info("DispatchUrl = " + dispatchUrl);

		RequestDispatcher dispatcher = req.getRequestDispatcher(dispatchUrl);
		dispatcher.forward(req, res);
	}

	private String baseUrlOf(HttpServletRequest req)
	{
		String url = req.getRequestURL().toString();
		return url.substring(0, url.length() - req.getRequestURI().length()) + req.getContextPath();
	}
}
