| Endpoint                          | Token esperado | Rol requerido   |
| --------------------------------- | -------------- | --------------- |
| `/api/v1/users/actions/login`     | Libre          | Ninguno         |
| `/api/v1/users/mgmt/actions/**`   | Token ROOT     | `ROOT`          |
| `/api/v1/actions/change-password` | Token común    | cualquiera      |
| `/api/v1/**` (otros)              | Token común    | depende del rol |
