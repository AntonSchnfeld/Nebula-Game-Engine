#version 330 core

layout (location = 0) in vec3 aPos;
layout (location = 1) in vec4 aCol;

uniform mat4 aView;
uniform mat4 aProjection;

out vec4 fCol;

void main()
{
    fCol = aCol;
    gl_Position = aView * aProjection * vec4(aPos, 1.0f);
}