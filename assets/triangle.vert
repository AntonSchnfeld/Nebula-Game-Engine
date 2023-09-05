#version 330 core

layout (location = 0) in vec3 aPos;
layout (location = 1) in vec4 aCol;
layout (location = 2) in vec2 aUV;

out vec4 fCol;
out vec2 fUV;

void main()
{
    fCol = aCol;
    fUV = aUV;
    gl_Position = vec4(aPos, 1.0f);
}