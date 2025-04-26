# GitPOD

```shell
#!/bin/bash
cp /workspace/.bash_aliases $HOME/
sudo apt install rsync
```

# Keycloak

**Ignore SSL connection in dev mode**

```sql
update REALM
set ssl_required='NONE';
```
