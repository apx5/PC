# PC
    -Server
    [x] - Criar server multi-thread
    [x] - Menu, registo e login do cliente
    [] - Controlo de concorrência
        [x] - Quando o cliente faz o registo temos de verificar se já existe username, temos de bloquear o hashmap do registo de clientes para consultar se já existe aquele user e para garantir que ninguem se regista com o mesmo user ao mesmo tempo 
        [x] - Quando o cliente faz login temos de consultar o hashmap e ver se a pass coincide(aqui é só leitura, não há necessidade de concorrência
        [ ] - Quando o cliente manda novos dados temos de bloquear o hashmap dos registos de dados, adicionar o novo valor para aquele user(valor += (novo-actual)) (não precisa de bloquear na consulta do valor actual) , e enviar os novos dados a todos os outros clientes
    [x] - Se houver tempo fazer a estimativa por zonas do país(criação de classe User com username, password, zona(norte, centro, sul)
    [ ] - Como vamos organizar o servidor para trabalhar com threads? (o prof tinha falado em ter duas threads por cliente, não percebi bem porquê)





    - Client
    [x] - conseguir registar-se e fazer login
    [x] - Assume-se que cada vez que o cliente manda dados, manda novos casos que conhece. Se quiser remover dos casos, dá um valor negativo
    [x] - Dar possibilidade ao cliente de consultar a informação em vez de apenas aguardar a visualização quando outros clientes inserem os seus dados

