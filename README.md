# water-tool

> 上善若水，水善利万物而不争，处众人之所恶，故几于道。

Java 8 通用工具类库，为日常 Java 开发提供常用工具方法集合。

## Maven 依赖

```xml
<dependency>
    <groupId>io.github.loulangogogo</groupId>
    <artifactId>water-tool</artifactId>
    <version>0.0.7</version>
</dependency>
```

## 模块说明

| 模块 | 工具类 | 说明 |
|------|--------|------|
| `tool` | `StrTool`、`ObjectTool`、`AssertTool`、`ReflectTool`、`IdTool`、`CharsetTool`、`TreeTool` | 核心工具：字符串操作、空值检查、断言、反射、ID生成、字符集、树结构 |
| `bean` | `BeanTool`、`BeanPropertiesTool`、`JsonBeanTool`、`SerializeTool` | Bean 操作：属性拷贝、Map转换、JSON转换、序列化 |
| `json` | `JsonTool`、`JsonMap`、`JSON` | 基于 Jackson 的 JSON 序列化/反序列化工具 |
| `crypto` | `AESTool`、`RSATool`、`MD5Tool`、`SHA2Tool`、`SHA3Tool`、`Base64Tool` | 加密解密：对称/非对称加密、摘要算法、Base64 |
| `collection` | `CollectionTool`、`CollTool`、`ArrayTool` | 集合与数组操作工具 |
| `date` | `DateTool`、`DateFormatTool`、`LocalDateAndTimeTool`、`LocalDateAndTimeFormatTool` | 日期时间工具，同时支持 `java.util.Date` 和 `java.time` API |
| `io` | `FileTool`、`IoTool`、`ZipTool`、`ResourceTool` | 文件/IO 操作：文件处理、流操作、压缩解压、资源加载 |
| `math` | `BigDecimalTool`、`MathTool`、`WeightRandom` | 数学工具：高精度运算、数学计算、权重随机 |
| `thread` | `ThreadPoolTool`、`ThreadTool`、`ExecutorBuilder`、`NamedThreadFactory` | 线程池管理：构建器模式创建线程池 |
| `stream` | `CollectorTool` | Stream 收集器工具 |
| `map` | `MapTool` | Map 操作工具 |
| `enums` | `CharsetEnum`、`MaskingDataTypeEnum`、`SignAlgorithm` | 通用枚举：字符集、脱敏数据类型、签名算法 |
| `exception` | `JsonException`、`EncryptException`、`DecryptException` 等 | 各模块自定义运行时异常 |
| `interfaces` | `Builder<T>`、`TreeNode<T>` | 通用接口定义 |

## 版本历史

### 0.0.7

**新增**
- `RSATool` 扩展支持多种签名算法（RSA、DSA、ECDSA），所有方法新增 `SignAlgorithm` 参数重载，默认使用 `SHA256withRSA`
- 新增 `SignAlgorithm` 签名算法枚举，涵盖 RSA/DSA/ECDSA 及 RSA-PSS 等算法组合
- `StrTool` 新增字符串前缀判断（`startsWith`/`startsWithIgnoreCase`）和子串查找（`indexOf`/`lastIndexOf`/`lastIndexOfIgnoreCase`）方法

**测试**
- 新增 `RSATool` 多算法签名/验签、加密/解密完整测试
- 新增 `StrTool` 新增方法的单元测试

### 0.0.6

**修复**
- 修复 `BeanPropertiesTool` 属性复制异常处理
- 修复 `BeanTool` 中 `beanToMap` 方法调用错误
- 修复集合去重功能的线程安全问题

**优化**
- 优化工具类代码实现（`StrTool`、`ObjectTool`、`CollTool` 等）
- 优化 `ExecutorBuilder` 线程池构建器
- 优化 `ZipTool` 压缩工具
- 配置 Maven 插件编码设置

**文档**
- 为异常类和枚举添加详细文档注释
- 为 AES 加密工具类添加枚举文档注释
- 更新 `CollTool` 泛型文档

**测试**
- 新增大量单元测试覆盖：Bean、Collection、Crypto、Date、IO、JSON、Map、Math、Stream、Thread、Assert、Charset、Id、Object、Reflect、Str、Tree 等模块

**其他**
- 添加 `.idea/` 目录到 `.gitignore`

### 0.0.5

- 初始发布版本

## 许可证

本项目采用开源许可。
