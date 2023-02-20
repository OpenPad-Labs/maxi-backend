package io.maxi.api.utils;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * 自定义插件
 * 增加
 * 1. find
 * 2. list
 * 3. select one
 *
 * 参考
 * @see org.mybatis.generator.plugins.ToStringPlugin
 * @author My
 */
public class SelectOnePlus extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        AbstractXmlElementGenerator elementGenerator = new CustomAbstractXmlElementGenerator();
        elementGenerator.setContext(context);
        elementGenerator.setIntrospectedTable(introspectedTable);
        elementGenerator.addElements(document.getRootElement());
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    @Override
    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable)  {
        AbstractJavaMapperMethodGenerator methodGenerator = new CustomJavaMapperMethodGenerator();
        methodGenerator.setContext(context);
        methodGenerator.setIntrospectedTable(introspectedTable);
        methodGenerator.addInterfaceElements(interfaze);
        return super.clientGenerated(interfaze, introspectedTable);
    }
}


class CustomAbstractXmlElementGenerator extends AbstractXmlElementGenerator {

    @Override
    public void addElements(XmlElement parentElement) {
        // 增加base_query
        XmlElement sql = new XmlElement("sql");
        sql.addAttribute(new Attribute("id", "base_query"));
        //在这里添加where条件
        XmlElement selectTrimElement = new XmlElement("trim"); //设置trim标签
        selectTrimElement.addAttribute(new Attribute("prefix", "WHERE"));
        selectTrimElement.addAttribute(new Attribute("prefixOverrides", "AND | OR")); //添加where和and
        StringBuilder sb = new StringBuilder();
        for(IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns()) {
            XmlElement selectNotNullElement = new XmlElement("if"); //$NON-NLS-1$
            sb.setLength(0);
            sb.append("null != ");
            sb.append(introspectedColumn.getJavaProperty());
            selectNotNullElement.addAttribute(new Attribute("test", sb.toString()));
            sb.setLength(0);
            // 添加and
            sb.append(" and ");
            // 添加别名t
            sb.append("t.");
            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
            // 添加等号
            sb.append(" = ");
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            selectNotNullElement.addElement(new TextElement(sb.toString()));
            selectTrimElement.addElement(selectNotNullElement);
        }
        sql.addElement(selectTrimElement);
        parentElement.addElement(sql);

        // 公用select
        sb.setLength(0);
        sb.append("select ");
        sb.append("t.* ");
        sb.append("from ");
        sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        sb.append(" t");
        TextElement selectText = new TextElement(sb.toString());

        // 公用include
        XmlElement include = new XmlElement("include");
        include.addAttribute(new Attribute("refid", "base_query"));

        // 增加find
        XmlElement find = new XmlElement("select");
        find.addAttribute(new Attribute("id", "find"));
        find.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        find.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
        find.addElement(selectText);
        find.addElement(include);
        parentElement.addElement(find);

    }

}


class CustomJavaMapperMethodGenerator extends AbstractJavaMapperMethodGenerator {

    @Override
    public void addInterfaceElements(Interface interfaze) {

        addInterfaceFind(interfaze);

    }

    private void addInterfaceFind(Interface interfaze) {
        // 先创建import对象
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        // 添加Lsit的包
        importedTypes.add(FullyQualifiedJavaType.getNewListInstance());
        // 创建方法对象
        Method method = new Method("find");
        // 设置该方法为public
        method.setVisibility(JavaVisibility.PUBLIC);
        // 设置返回类型是List
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        // 方法对象设置返回类型对象
        method.setReturnType(returnType);
        // 设置方法名称为我们在IntrospectedTable类中初始化的 “selectByObject”

        // 设置参数类型是对象
        FullyQualifiedJavaType parameterType;
        parameterType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        // import参数类型对象
        importedTypes.add(parameterType);
        // 为方法添加参数，变量名称record
        method.addParameter(new Parameter(parameterType, "record")); //$NON-NLS-1$
        //
        addMapperAnnotations(interfaze, method);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        if (context.getPlugins().clientSelectByPrimaryKeyMethodGenerated(method, interfaze, introspectedTable)) {
            interfaze.addImportedTypes(importedTypes);
            interfaze.addMethod(method);
        }
    }


    public void addMapperAnnotations(Interface interfaze, Method method) {
    }
}