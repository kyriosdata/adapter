# oes
Serialização/desserialização de objetos do Modelo de Referência do 
openEHR.

[<img src="https://api.travis-ci.org/kyriosdata/oes.svg?branch=master">](https://travis-ci.org/kyriosdata/oes)
[![Dependency Status](https://www.versioneye.com/user/projects/581bd12dafb6141c1c4bf023/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/581bd12dafb6141c1c4bf023)
[![Sonarqube](https://sonarqube.com/api/badges/gate?key=com.github.kyriosdata.oes:oes)](https://sonarqube.com/dashboard/index?id=com.github.kyriosdata.oes%3Aoes)
[![Javadocs](http://javadoc.io/badge/com.github.kyriosdata.oes/oes.svg)](http://javadoc.io/doc/com.github.kyriosdata.oes/oes)

<br />
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">
<img alt="Creative Commons License" style="border-width:0"
 src="https://i.creativecommons.org/l/by/4.0/88x31.png" /></a>
 <br />This work is licensed under a <a rel="license" 
 href="http://creativecommons.org/licenses/by/4.0/">Creative Commons 
 Attribution 4.0 International License</a>. 
 <br />Fábio Nogueira de Lucena - Fábrica de Software - 
 Instituto de Informática (UFG).

## Caso de uso
O Modelo de Referência (MR) do openEHR é um conjunto de classes que 
define os blocos básicos para empacotamento de informações em saúde.
Quando essas informações precisam ser transferidas ou mesmo persitidas,
existem várias possibilidades. O presente projeto apresenta uma 
alternativa dentre aquelas existentes. 

## Como usar (via maven)?

Acrescente a dependência no arquivo pom.xml:

<pre>
&lt;dependency&gt;
  &lt;groupId&gt;com.github.kyriosdata.oes&lt;/groupId&gt;
  &lt;artifactId&gt;oes&lt;/artifactId&gt;
  &lt;version&gt;1.0.0&lt;/version&gt;
&lt;/dependency&gt;
</pre>
