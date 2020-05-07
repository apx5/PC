# PC
    -Server
    [ ] - Criar server multi-thread
    [ ] - Menu, registo e login do cliente
    [ ] - Controlo de concorrência
        [ ] - Quando o cliente faz o registo temos de verificar se já existe username, temos de bloquear o hashmap do registo de clientes para garantir que ninguem se regista com o mesmo user ao mesmo tempo
        [ ] - Quando o cliente manda novos dados temos de bloquear o hashmap dos registos de dados, adicionar o novo valor para aquele user(valor += (novo-actual)), e enviar os novos dados a todos os outros clientes
    [ ] - Se houver tempo fazer a estimativa por zonas do país
