# Poc-springvault

test with https://shanbhagsudharshan.medium.com/integrate-hashicorp-vault-with-spring-config-server-30993baa55b0

```bash
# start vault
docker run -p 8200:8200  --name vault --cap-add=IPC_LOCK \
-e 'VAULT_DEV_ROOT_TOKEN_ID=00000000-0000-0000-0000-000000000000' \
-e 'VAULT_DEV_LISTEN_ADDRESS=0.0.0.0:8200' vault

## install if needed
yay -S vault
export VAULT_ADDR='http://127.0.0.1:8200' \
export VAULT_TOKEN="00000000-0000-0000-0000-000000000000"
```

## Webui

[doc youtube](https://www.youtube.com/watch?v=gA2tBDKqH0s)

* creation d'une Policies ACL

```
# spring-policy-read
path "kv/*" {
  capabilities = ["list"]
}
path "kv/data/poc-spring/*" {
  capabilities = ["read", "list"]
}
```

## approle

### utilisation

Approle est une des méthodes d'authentification possible avec spring.

Il nécessite soit de connaitre que le roleID, soit de connaitre le couple roleID/secretId.

Le secretId est similaire à un token qui a une durée de vie limité dans le temps.

### webuit

http://localhost:8200/ui/vault/access
* enable new method Approle


### Console
```
vault auth enable approle
vault write auth/approle/role/poc-spring \
    secret_id_ttl=10m \
    token_num_uses=10 \
    token_ttl=20m \
    token_max_ttl=30m \
    secret_id_num_uses=40 \
    policies="spring-policy-read, default"
## get role id
vault read auth/approle/role/poc-spring/role-id
## Get a SecretID
vault write -f auth/approle/role/poc-spring/secret-id
```
## Create secret

create kv

poc-spring/dev  content: dev
poc-spring/prod content: prod
poc-spring/preprod content: preprod


## test

run spring

```
mvn spring-boot:run  -Dspring-boot.run.profiles=dev
```
