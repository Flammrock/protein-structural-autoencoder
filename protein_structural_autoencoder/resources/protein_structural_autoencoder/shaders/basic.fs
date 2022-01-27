#version 330 core

out vec4 fragColor;
in vec3 vertexColor;
in vec3 vertexNormal;
in vec3 fragPos;

uniform vec3 lightPos;
uniform vec3 lightColor;

void main() {

	float ambientStrength = 0.1;
    vec3 ambient = ambientStrength * lightColor;

	vec3 norm = normalize(vertexNormal);
    vec3 lightDir = normalize(lightPos - fragPos);
    float diff = max(dot(norm, lightDir), 0.0);
    vec3 diffuse = diff * lightColor + 0.1;

	fragColor = vec4((ambient + diffuse) * vertexColor, 1.0);
}