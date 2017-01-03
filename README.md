# adapter
Adaptador do Modelo de Referência (MR) do openEHR para Seed.

[<img src="https://api.travis-ci.org/kyriosdata/adapter.svg?branch=master">](https://travis-ci.org/kyriosdata/adapter)
[![Dependency Status](https://www.versioneye.com/user/projects/581cb3834304530ad3a5549b/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/581cb3834304530ad3a5549b)
[![Sonarqube](https://sonarqube.com/api/badges/gate?key=com.github.kyriosdata.adapter%3Aoe-seed)](https://sonarqube.com/dashboard/index?id=com.github.kyriosdata.adapter%3Aoe-seed)
[![Javadocs](http://javadoc.io/badge/com.github.kyriosdata.adapter/oe-seed.svg)](http://javadoc.io/doc/com.github.kyriosdata.adapter/oe-seed)

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
existem várias possibilidades.  

## Como usar (via maven)?

Acrescente a dependência no arquivo pom.xml:

<pre>
&lt;dependency&gt;
  &lt;groupId&gt;com.github.kyriosdata.adapter&lt;/groupId&gt;
  &lt;artifactId&gt;oe-seed&lt;/artifactId&gt;
  &lt;version&gt;1.0.0&lt;/version&gt;
&lt;/dependency&gt;
</pre>

#### Tipos de campos
Um campo de um registro é definido pelo tipo correspondente. 
Os tipos disponíveis são definidos na tabela abaixo. Seguindo a
ordem das colunas abaixo tem-se o nome da constante que define o tipo, 
o valor dessa para o tipo e, por último, o tipo correspondente na
linguagem Java.

| Constante   | Valor |   Java   |
|:----:  |:-----:|:--------:|
| BOOL   |  0    |  boolean |
| BYTE   |  1    |  byte    |
| SHORT  |  2    |  short   |
| INT    |  3    |  int     |
| LONG   |  4    |  long    |
| FLOAT  |  5    |  float   |
| DOUBLE |  6    |  double  |
| CHAR   |  7    |  char    |
| STRING |  8    |  String  |
| VECTOR |  9    |  byte[]  |

#### Block
A classe _Block_ encapsula operações sobre um vetor de bytes (_buffer_) e 
possui métodos para depositar a partir de determinada posição os bytes 
correspondentes a cada um dos tipos acima e, no sentido inverso, a partir de determinada
posição, recuperar o valor depositado. Convém observar, contudo, que essa
classe não sabe o tipo de um valor depositado em uma dada posição. Essa
informação cabe a um registro. 

#### Formato de registro
O formato de um registro é definido por uma sequência de tipos de campos. 
Por exemplo, para um registro formado por dois valores lógicos a
sequência de tipos é dada por 00. Por outro lado, se o registro é definido 
por um INT e um VECTOR (vetor de bytes), então a sequência de tipos correspondente é 39. 
Observe que não se trata do número 39, mas da sequência do tipo INT (3) seguido do tipo 
VECTOR (9). Mais um exemplo: o registro formado por três sequências de caracteres (STRING) 
seguidas por um DOUBLE tem como formato 8886.

#### Tamanho de um registro
Se o formato de um registro não inclui os tipos de valores 8 e 9, respectivamente,
STRING e VECTOR, então o registro possui tamanho fixo. Caso contrário, varia o tamanho 
do registro que possui pelo menos um campo de um desses tipos. Por exemplo, o formato
de registro definido por uma única STRING que deve registrar um logradouro, em um
registro pode ter como valor "Avenida T2", em outro registro "Avenida Paulista", de 
tamanho distinto. 

#### Representação (serialização) de um registro
Um registro é representado por um _header_ seguido dos dados correspondentes aos 
tipos do registro, conforme ilustrado abaixo.

```
+-----------------+
|      RECORD     |
+-----------------+
| Header | Fields |
+-----------------+
```

O _header_ é relevante para localização dos campos do registros e comentado logo abaixo, após
detalhamento de como os campos são representados. 

Abaixo segue a ilustração de um registro, sem o _header_, cujo formato é 388, ou seja, um INT seguido de
duas sequências de caracteres (STRING). O valor do INT é 23 (faz uso de 4 bytes); a primeira STRING apenas
4 bytes ("nome") e a segunda STRING ocupa outros 7 bytes ("contato"). A primeira linha indica os 
deslocamentos do início de cada campo com base na posição inicial (0). Ou seja, o inteiro faz 
uso dos bytes de 0 a 3 (inclusive), o "nome" ocupa os quatro bytes seguintes de 4 a 7 (inclusive) e, 
por último, "contato" faz uso dos bytes de 8 a 14 (inclusive). Nesse arranjo observe que o tipo
de tamanho fixo (INT) segue antes dos demais, ambos de tamanhos variáveis.

```
+------------------------------+
| HEADER |       FIELDS        |
+------------------------------+
---------|0---|4-----|8--------|15
+------------------------------+
| HEADER | 23 | nome | contato |
+------------------------------+
```

O _header_ obrigatoriamente inclui o tipo que identifica
o formato do registro. Observe que não é o formato propriamente dito, mas um
identificador que permite localizar o formato empregado pelo registro. Em alguns
casos o tipo é suficiente para localizar os campos no registro, não sendo necessário
nenhum outro valor adicional. Por exemplo, se um determinado tipo de registro possui
um único campo lógico (BOOL), então um exemplo é fornecido abaixo, onde TIPO 
unicamente identifica esse registro formado por apenas um campo do tipo BOOL.

```
+----------+
|  HEADER  | 
+----------+
|------|0--|1
+----------+
| TIPO | 1 | 
+----------+
```

Em outros cenário apenas a indicação do tipo não é suficiente, isso ocorre quando
campos de tamanho variável são empregados pelo registro. Nesses caso, o _header_ deve 
conter outras informações:
- *Tipo*, conforme comentado acima.
- *Tamanho do registro*. Permite rapidamente "saltar" para o próximo registro. Observe que esse valor pode
ser "recuperado" a partir do percurso do conteúdo do registro. 
- *Apontadores* para campos do formato do registro que seguem campos de tamanho variável.

A ilustração acima, acrescida do _header_, é fornecida abaixo. Suponha que o tipo de valor 54
identifica unicamente o formato 388.

```
+-----------------------------------+
|   HEADER    |      FIELDS         |
+-----------------------------------+
--------------|0---|4-----|8--------|15
+-----------------------------------+
| 54 | 15 | 8 | 23 | nome | contato |
+-----------------------------------+
```

Interpretação de cada um dos valores acima:
- Primeiro segue o tipo do registro, valor 54. 
- O tipo é seguido do tamanho do registro, 15 bytes de dados. Esse tamanho não é o
tamanho total do registro, pois não inclui os bytes empregados pelo _header_.
- O último valor do _header_ é 8, a posição inicial do campo "contato". Observe que ao
manter os campos de tamanho fixo no início, em ordem bem definida, não é necessário
indicar a posição deles, nem do primeiro de tamanho variável, nesse caso "nome". Ou seja,
para um registro do tipo 54 é suficiente armazenar a posição de início do último campo,
posição 8.

> Decisões
> * Campos de tamanho fixo precedem todos os campos de tamanho variável.
> * Valor de posição no header é relativa à posição inicial (0) dos dados.

O registro representado na ilustração acima fornece o comportamento geral. 
Contudo, há situações especiais que demandam alteração na representação tanto
do _header_ quanto do dados. Contudo, isso é melhor compreendido após a introdução
de outras questões: (a) blocos e (b) fragmentação de registros.  

#### Blocos (elemento de divisão de um arquivo)
Uma base de dados é armazenada em um arquivo didivido em blocos de tamanho fixo. O tamanho padrão é 4KB. O acesso ao conteúdo da base de dados significa que esses blocos precisam ser transferidos para a memória RAM. No sentido inverso, atualizações precisam ser depositadas no bloco correspondente no arquivo em questão.

#### Fragmentação de registro
Dado que apenas parte da informação de uma base de dados se encontra em RAM e que um bloco possui tamanho fixo, enquanto os registros não, é natural que a divisão em blocos "fragmente" um registro no sentido em que parte das informações podem estar no final de um bloco e as demais a partir do início do bloco seguinte. 

> Decisão
> * Apenas dados podem estar em blocos distintos, _header_ sempre em um único bloco.
> * Dado de tamanho superior ao tamanho de um bloco é armazenado em área específica (_large data file_).
> * Registro pode ser fragmentado, campo não.

Abaixo é ilustrado o cenário onde o registro está disposto em dois blocos, 
o bloco 6 e o bloco 7 (sem perda de generalidade). 
Nessa ilustração, a STRING "contato" é
dividida em "cont" (bloco 6) e "ato" (bloco 7). Essa divisão, contudo, não é permitida,
conforme decisão acima.

```
------------ Bloco 6 -----------||----------- Bloco 7 ------------
--------------|0---|4-----|8----||----|15
+-------------------------------------+
| 54 | 15 | 8 | 23 | nome | cont||ato |
+-------------------------------------+
```

A estratégia acima tem como positivo o fato de fazer uso útil de cada byte de um bloco.
Uma alternativa é fornecida abaixo, onde um campo não é fragmentado, ao contrário da ilustração
acima e, em consequência, introduz "área" não preenchida ao final de um bloco.
Observe que, se campos são "pequenos", então essa área é mínima.

```
------------ Bloco 6 ------------||----------- Bloco 7 ------------
---------------|0---|4-----|8----||12
+------------------------------------------+
| 54 | 15 | 12 | 23 | nome |     ||contato |
+------------------------------------------+
```


#### Posição
Uma posição indica o início de um campo. A posição de um registro de tamanho fixo
não é registrada no _header_, pois é fixa para cada tipo de registro (o deslocamento a partir 
do início do registro pode ser computado facilmente). A posição de um campo de tamanho
variável, por outro lado, exige que fragmentos sejam contemplados, conforme comentado acima.

Dado que dados "grandes" (maiores que o tamanho de um bloco são armazenados em área distinta), 
a posição de um campo de tamanho variável exige, no máximo, a identificação de 2 fragmentos.
Abaixo segue uma ilustração desses fragmentos, ainda não considerados pelo _header_.

```
|----------- Bloco 6 -----------||----------- Bloco 7 -----------|
|-------------------------| F1  || F2 |15
|-------------|0---|4-----|8----||----|15
+-------------------------------------+
| 54 | 15 | 8 | 23 | nome | cont||ato |
+-------------------------------------+
```

Na ilustração acima, sem perda de generalidade, são exibidos 2 blocos, o bloco 6 e o bloco 7. 
Adicionalmente são identificados dois fragmentos, o fragmento F1 (bytes de 8 a 11, inclusive) e
o fragmento F2 (bytes de 12 a 14, inclusive). O fragmento F1 reside no bloco 6 e o fragmento F2
no bloco 7.

