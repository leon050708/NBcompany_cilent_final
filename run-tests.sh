#!/bin/bash

# NBCompany项目测试运行脚本
# 作者: AI Assistant
# 日期: 2025-07-10

echo "🚀 NBCompany项目测试运行脚本"
echo "================================"

# 检查Java版本
echo "📋 检查Java版本..."
java -version
echo ""

# 检查Maven版本
echo "📋 检查Maven版本..."
./mvnw -version
echo ""

# 清理并编译项目
echo "🔧 清理并编译项目..."
./mvnw clean compile
if [ $? -ne 0 ]; then
    echo "❌ 编译失败，请检查代码错误"
    exit 1
fi
echo "✅ 编译成功"
echo ""

# 运行测试
echo "🧪 运行单元测试..."
./mvnw test
if [ $? -ne 0 ]; then
    echo "❌ 测试失败，请检查测试代码"
    exit 1
fi
echo "✅ 所有测试通过"
echo ""

# 生成覆盖率报告
echo "📊 生成覆盖率报告..."
./mvnw jacoco:report
if [ $? -ne 0 ]; then
    echo "❌ 覆盖率报告生成失败"
    exit 1
fi
echo "✅ 覆盖率报告生成成功"
echo ""

# 显示覆盖率统计
echo "📈 覆盖率统计摘要:"
echo "=================="
if command -v grep &> /dev/null; then
    echo "总体覆盖率:"
    grep "Total" target/site/jacoco/index.html | head -1
    echo ""
    
    echo "各模块覆盖率:"
    cat target/site/jacoco/jacoco.csv | head -15
else
    echo "覆盖率报告已生成，请查看 target/site/jacoco/index.html"
fi
echo ""

# 打开覆盖率报告
echo "🌐 正在打开覆盖率报告..."
if command -v open &> /dev/null; then
    open target/site/jacoco/index.html
    echo "✅ 覆盖率报告已在浏览器中打开"
elif command -v xdg-open &> /dev/null; then
    xdg-open target/site/jacoco/index.html
    echo "✅ 覆盖率报告已在浏览器中打开"
else
    echo "📁 覆盖率报告位置: target/site/jacoco/index.html"
    echo "请手动在浏览器中打开该文件"
fi
echo ""

echo "🎉 测试运行完成！"
echo "📁 覆盖率报告: target/site/jacoco/index.html"
echo "📁 测试报告: target/surefire-reports/"
echo "📁 覆盖率数据: target/jacoco.exec" 