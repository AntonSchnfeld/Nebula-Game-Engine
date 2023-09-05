#version 330 core

layout (location = 0) in vec3 aPosition;
layout (location = 1) in vec4 aColour;
layout (location = 2) in vec2 aUV;
layout (location = 3) in float aTextureID;

uniform mat4 aViewMatrix;
uniform mat4 aProjectionMatrix;

out vec4 fColour;
out vec2 fUV;
out float fTextureID;

void main ()
{
    fColour = aColour;
    fUV = aUV;
    fTextureID = aTextureID;

    gl_Position = aViewMatrix * aProjectionMatrix * vec4(aPosition, 1.0);
}