package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta charset=\"utf-8\">\n");
      out.write("        <title>Cosmetics</title>\n");
      out.write("        <link rel=\"stylesheet\" href=\"css/main.css\"/>\n");
      out.write("        <link rel=\"icon\" href=\"img/icon.jpeg\">\n");
      out.write("    </head>\n");
      out.write("    <div class=\"header\">\n");
      out.write("        <div class=\"container\">\n");
      out.write("            <div style=\"float: right\"><img alt=\"a\" src=\"img/phone.png\" class=\"himg\"/><a href=\"#\" class=\"hlink\">0523527327</a></div>\n");
      out.write("            <div style=\"float: right\"><img alt=\"a\" src=\"img/facb.png\" class=\"himg\"/><a href=\"#\" class=\"hlink\">&nbsp;&nbsp;BestComestic</a></div>\n");
      out.write("            <div style=\"float: left\"><a href=\"#\" class=\"hlink\">Destination to find the best Cosmetics in the world</a></div>\n");
      out.write("        </div>\n");
      out.write("    </div>\n");
      out.write("    <div class=\"body\">\n");
      out.write("        <div class=\"top\">\n");
      out.write("            <div class=\"logo\">\n");
      out.write("                <p id=\"logoname\">Cosmetics</p>\n");
      out.write("            </div>\n");
      out.write("            <div class=\"search\">\n");
      out.write("                <form style=\"line-height: 85px;\">\n");
      out.write("                    <input class=\"textsearch\" placeholder=\"Tìm kiếm: kem, sữa dưỡng da,...\"/>\n");
      out.write("                    <input class=\"buttonsearch\" type=\"button\" value=\"Tìm Kiếm\">\n");
      out.write("                </form>\n");
      out.write("            </div>\n");
      out.write("            <div class=\"login\"><a class=\"linkdangnhap\" href=\"#\">Đăng nhập / Đăng ký</a></div>\n");
      out.write("        </div>\n");
      out.write("        <div class=\"menu\">\n");
      out.write("            <ul style=\"padding-left: 185px\">\n");
      out.write("                <li><a href=\"#home\">Home</a></li>\n");
      out.write("                <li class=\"dropdown\"><a href=\"javascript:void(0)\" class=\"dropbtn\">Category</a>\n");
      out.write("                    <div class=\"dropdown-content\"><a href=\"#\">Link 1</a><a href=\"#\">Link 2</a><a href=\"#\">Link 3</a></div>\n");
      out.write("                </li>\n");
      out.write("                <li class=\"dropdown\"><a href=\"javascript:void(0)\" class=\"dropbtn\">Skin Type</a>\n");
      out.write("                    <div class=\"dropdown-content\"><a href=\"#\">Link 1</a><a href=\"#\">Link 2</a><a href=\"#\">Link 3</a></div>\n");
      out.write("                </li>\n");
      out.write("                <li class=\"dropdown\"><a href=\"javascript:void(0)\" class=\"dropbtn\">Skin Concern</a>\n");
      out.write("                    <div class=\"dropdown-content\"><a href=\"#\">Link 1</a><a href=\"#\">Link 2</a><a href=\"#\">Link 3</a></div>\n");
      out.write("                </li>\n");
      out.write("            </ul>\n");
      out.write("        </div>\n");
      out.write("    </div>\n");
      out.write("    <div class=\"main\"><img alt=\"\" src=\"img/home.jpg\"/>\n");
      out.write("        <div class=\"mainlist\">\n");
      out.write("            <div class=\"listproduct\">\n");
      out.write("                <div class=\"product\"><img class=\"imgproduct\" src=\"img/132.gif\"/>\n");
      out.write("                    <p class=\"productname\">Sản phẩm loại bỏ tế bào chết cho da nhạy cảm Calm Redness Relief</p>\n");
      out.write("                    <p class=\"productcate\">Sữa dưỡng ẩm</p>\n");
      out.write("                    <p class=\"productbrand\">Paula’s Choice</p>\n");
      out.write("                    <p class=\"productprice\">910.000đ</p>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("            <div style=\"float: left;width: 100%;\">\n");
      out.write("                <input class=\"buttonPage\" type=\"button\" id=\"first\" onclick=\"firstPage()\" value=\"first\" />\n");
      out.write("                <input class=\"buttonPage\" type=\"button\" id=\"next\" onclick=\"nextPage()\" value=\"next\" />\n");
      out.write("                <input class=\"buttonPage\" type=\"button\" id=\"previous\" onclick=\"previousPage()\" value=\"previous\" />\n");
      out.write("                <input class=\"buttonPage\" type=\"button\" id=\"last\" onclick=\"lastPage()\" value=\"last\" />\n");
      out.write("            </div>\n");
      out.write("        </div>\n");
      out.write("    </div>\n");
      out.write("    <div class=\"footer\"><img style=\"height: 30px\" src=\"img/dathongbao.png\"/>\n");
      out.write("        <p>© 2017 - Bản quyền của Công Ty Cao Văn Phú</p>\n");
      out.write("        <p>Giấy chứng nhận ĐKKD số 0314414731 do Sở Kế hoạch và Đầu tư Tp. HCM cấp ngày 19/05/2017</p>\n");
      out.write("    </div>\n");
      out.write("</body>\n");
      out.write("</html>\n");
      out.write("\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
