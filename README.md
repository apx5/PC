# PC
    -Server
    [ ] - Criar server multi-thread
    [ ] - Menu, registo e login do cliente
    [ ] - Controlo de concorrência
        [ ] - Quando o cliente faz o registo temos de verificar se já existe username, temos de bloquear o hashmap do registo de clientes para consultar se já existe aquele user e para garantir que ninguem se regista com o mesmo user ao mesmo tempo 
        [ ] - Quando o cliente faz login temos de consultar o hashmap e ver se a pass coincide(aqui é só leitura, não há necessidade de concorrência
        [ ] - Quando o cliente manda novos dados temos de bloquear o hashmap dos registos de dados, adicionar o novo valor para aquele user(valor += (novo-actual)) (não precisa de bloquear na consulta do valor actual) , e enviar os novos dados a todos os outros clientes
    [ ] - Se houver tempo fazer a estimativa por zonas do país(criação de classe User com username, password, zona(norte, centro, sul)
    [ ] - Como vamos organizar o servidor para trabalhar com threads? (o prof tinha falado em ter duas threads por cliente, não percebi bem porquê)





    - Client
    [ ] - conseguir registar-se e fazer login
    [ ] - Assume-se que cada vez que o cliente manda dados, está a mandar o total de casos que conhece(infectados/150) e nao os novos casos que conhece em relação ao que já tinha inserido
    [ ] - Dar possibilidade ao cliente de consultar a informação em vez de apenas aguardar a visualização quando outros clientes inserem os seus dados

