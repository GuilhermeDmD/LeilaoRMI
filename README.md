# Leilão 
Aplicação de leilão feita em java, utilizando RMI, onde os participantes podem dar lances de forma simultânea.
Pontos importantes:
* O leilão está configurado para durar 3 min.
* O limite de lances é de 10, caso seja atingida a quantidade o leilão é encerrado.
* Ao dar um lance o participante sofre um bloqueio de 20seg e não consegue dar um novo lance logo em seguida.
## Como rodar:
Recomendamos utilizar o VSCode, além de possuir o javajdk instalado na sua máquina.
<br>
Para rodar certifique-se estar dentro do diretório src
* No terminal primeiro compile todos os arquivos java com o comando:
  ```
  javac *.java
  ````
* Dentro do mesmo terminal rode o servidor RMI utilizando:
  ```
  rmiregistry
  ```
* Abra um novo terminal e utilize o comando para inicializar o servidor de leilão:
  ```
  java LeilaoServidor
  ```
* Com o servidor rodando em outros terminais inicie diferentes participantes usando:
  ```
  java App
  ```
