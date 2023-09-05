#version 330 core

in vec4 fColour;
in vec2 fUV;
in float fTextureID;

uniform sampler2D texture1;
uniform sampler2D texture2;
uniform sampler2D texture3;
uniform sampler2D texture4;
uniform sampler2D texture5;
uniform sampler2D texture6;
uniform sampler2D texture7;
uniform sampler2D texture8;

out vec4 pixelColour;

void main ()
{
    if (fTextureID < 1)
    {
        pixelColour = fColour;
    }
    else if (fTextureID == 1) pixelColour = texture(texture1, fUV);
}