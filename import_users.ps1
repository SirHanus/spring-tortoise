docker cp TortoiseManager-realm.json keycloak-container:/tmp/TortoiseManager-realm.json

docker exec -it keycloak-container /opt/keycloak/bin/kc.sh import --file /tmp/TortoiseManager-realm.json

# Ensure the Keycloak container is running
docker run --name keycloak-container -p 8050:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin -d quay.io/keycloak/keycloak:24.0.4 start-dev

# Authenticate with Keycloak Admin CLI
docker exec -it keycloak-container /opt/keycloak/bin/kcadm.sh config credentials --server http://localhost:8080 --realm master --user admin --password admin

# Copy the users.json file into the container
docker cp .\users.json keycloak-container:/tmp/users.json

# Read the users.json file
$users = Get-Content -Raw -Path .\users.json | ConvertFrom-Json

# Iterate over each user and create them in Keycloak
foreach ($user in $users) {
    $username = $user.username
    $emailVerified = $user.emailVerified
    $enabled = $user.enabled
    $requiredActions = $user.requiredActions -join ","
    $attributes = $user.access

    # Create the user in Keycloak
    docker exec keycloak-container /opt/keycloak/bin/kcadm.sh create users -r TortoiseManager `
        -s username="$username" `
        -s emailVerified=$emailVerified `
        -s enabled=$enabled `
        -s attributes.'access'="$($attributes | ConvertTo-Json)"

    # Handle required actions separately
    if ($requiredActions) {
        $userId = docker exec keycloak-container /opt/keycloak/bin/kcadm.sh get users -r TortoiseManager -q username=$username --fields id --format csv | Select-String -Pattern "^\w{8}-\w{4}-\w{4}-\w{4}-\w{12}$"
        docker exec keycloak-container /opt/keycloak/bin/kcadm.sh update users/$userId -r TortoiseManager -s requiredActions="[$requiredActions]"
    }
}
