#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec3 color;
layout (location = 2) in vec3 normal;

uniform mat4 transformWorld;
uniform mat4 transformObject;
uniform mat4 cameraProjection;

uniform vec3 lightPos;  

out vec3 fragPos;
out vec3 vertexColor;
out vec3 vertexNormal;

void main() {

	fragPos = vec3(transformObject * vec4(position, 1.0));
	vertexColor = color;
	vertexNormal = mat3(transpose(inverse(transformObject))) * normal;

	gl_Position = cameraProjection * transformWorld * transformObject * vec4(position, 1.0);
	 
}