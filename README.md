# PC
Programação Concorrente 19/20

O trabalho consiste no desenvolvimento de um servidor que permita recolher estimativas da proporção de infectados numa pandemia.  A comunicação é orientada à linha. Cada cliente deve registar-se (passo efetuado uma única vez por cliente) e autenticar-se para se ligar ao servidor. De seguida, o servidor aguarda que o cliente indique quantos casos de doença este conhece nos seus contactos (valor inteiro >= 0). Sempre que algum cliente fornece informação ao servidor, o servidor envia a todos os clientes ligados uma nova estimativa da proporção média. (Para este cálculo assume-se que cada cliente conhece 150 contactos, pelo que um cliente que indique 15 casos, está a indicar a proporção de 0.1. O servidor reporta a média aritmética das proporções recebidas). Os cliente podem indicar valores de casos de doença as vezes que entenderem, até fecharem a conexão.

É importante realçar que as notificações são assíncronas e os clientes devem receber estas mensagens mesmo sem estar a fazer pedidos ao servidor. Por outras palavras, não se deve assumir que o servidor só envia estimativas globais como resposta a uma nova operação remota do cliente.

Ainda, o trabalho deve considerar a autenticação e registo de utilizadores, dado o seu nome e palavra-passe. Sempre que um utilizador desejar interagir com o serviço deverá estabelecer uma conexão e ser autenticado pelo servidor.

Como programa cliente pode utilizar o "nc" ou, se preferir, desenvolver o seu próprio programa.

Notas:

- Cada máquina cliente sabe o IP e Porto do servidor.
- Devem utilizar primitivas de programação com sockets, programação concorrente
e controlo de concorrência local sempre que necessário.
- Deve ser usada a linguagem de programação Java.
- Os grupos podem ser de 2 a 3 participantes.
- A submissão do trabalho (até às 13h00 do dia 3 de Junho) consta de um .zip
ou .tgz com o código fonte e um relatório .pdf de até 6 páginas. 
