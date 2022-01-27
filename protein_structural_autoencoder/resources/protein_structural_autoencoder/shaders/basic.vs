#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec3 color;

uniform mat4 transformWorld;
uniform mat4 transformObject;
uniform mat4 cameraProjection;

out vec3 vertexColor;

void main() {
	gl_Position = cameraProjection * transformWorld * transformObject * vec4(position,1);
	vertexColor = color;
}